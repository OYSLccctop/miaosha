package com.miaoshaproject.controller;

import com.alibaba.druid.util.StringUtils;
import com.miaoshaproject.controller.viewObject.UserVO;
import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.response.CommonReturnType;
import com.miaoshaproject.service.UserService;
import com.miaoshaproject.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller("user")
@RequestMapping("/user")
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate redisTemplate;

    // 本质是proxy，会让用户在每个线程中处理自己对应的request
    @Autowired
    private HttpServletRequest httpServletRequest;

    // 用户登录接口
    @RequestMapping(value = "/login", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType login(@RequestParam(name = "telphone")String telphone,
                                  @RequestParam(name = "password")String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {
        // 入参校验
        if (StringUtils.isEmpty(telphone) ||
            StringUtils.isEmpty(password)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        // 用户登录服务,用来校验用户登录是否合法
        UserModel userModel = userService.validateLogin(telphone, this.enCodeByMd5(password));
/*        // 将登录凭证加入到用户登录成功的session内
        this.httpServletRequest.getSession().setAttribute("IS_LOGIN",true);
        this.httpServletRequest.getSession().setAttribute("LOGIN_USER",userModel);*/
        // 修改为若用户登录成功后，将对应的登录信息和登录凭证一起存入redis中。

        // 生成登录凭证UUID
        String uuidToken = UUID.randomUUID().toString().replace("-", "");
        // 建立token和用户登录态之间的联系
        redisTemplate.opsForValue().set(uuidToken,userModel);
        redisTemplate.expire(uuidToken,1, TimeUnit.HOURS);

        return CommonReturnType.create(uuidToken);
    }


    //用户注册接口
    @RequestMapping(value = "/register", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType register(@RequestParam(name = "telphone") String telphone,
                                     @RequestParam(name = "otpCode") String otpCode,
                                     @RequestParam(name = "name") String name,
                                     @RequestParam(name = "gender") Byte gender,
                                     @RequestParam(name = "age") Integer age,
                                     @RequestParam(name = "password") String password) throws BusinessException, UnsupportedEncodingException, NoSuchAlgorithmException {

        //验证手机号和对应的otpCode相符合
        String inSessionOtpCode = (String) this.httpServletRequest.getSession().getAttribute(telphone);
        if (!com.alibaba.druid.util.StringUtils.equals(otpCode, inSessionOtpCode)) {  // 此处使用类库中的equals的方法是因为该类库中的方法已经做了判空处理
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "短信验证码不符合");
        }
        //用户的注册流程
        UserModel userModel = new UserModel();

        userModel.setName(name);
        userModel.setGender(gender);
        userModel.setAge(age);
        userModel.setTelphone(telphone);
        userModel.setRegisterMode("byphone");
        userModel.setEncrptPassword(this.enCodeByMd5(password));

        userService.register(userModel);
        return CommonReturnType.create(null);
    }

    //对密码进行加密
    public String enCodeByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确定一个计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();
        //加密字符串
        String newStr = base64Encoder.encode(md5.digest(str.getBytes("UTF-8")));
        return newStr;
    }


    // 用户获取otp短信接口
    @RequestMapping(value = "/getotp", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType getOtp(@RequestParam(name = "telphone") String telphone) {
        // 按照规则生成OTP验证码
        Random random = new Random();
        int randomInt = random.nextInt(99999);
        randomInt += 10000;
        String otpCode = String.valueOf(randomInt);
        // 将OTP验证码同对应用户的手机号关联,使用httpsession的方式绑定他的手机号和OTPCODE(真实项目使用redis)
        httpServletRequest.getSession().setAttribute(telphone, otpCode);
        // 将OTP验证码通过短信通道发送用户
        System.out.println("telphone = " + telphone + "& otpCode = " + otpCode);

        return CommonReturnType.create(null);
    }

    @RequestMapping("/get")
    @ResponseBody
    public CommonReturnType getUser(@RequestParam(name = "id") Integer id) throws BusinessException {
        // 调用service服务，获取对应id的用户对象，并返回给前端
        UserModel userModel = userService.getUserById(id);
        // 若获取的对应对象信息不存在
        if (userModel == null) {
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }
        // 将核心领域模型用户对象，转换为可供UI使用的viewObject
        UserVO userVO = convertFromModel(userModel);
        // 返回通用对象
        return CommonReturnType.create(userVO);
    }

    private UserVO convertFromModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel, userVO);
        return userVO;
    }
}
