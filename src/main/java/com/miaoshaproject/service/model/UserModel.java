package com.miaoshaproject.service.model;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class UserModel implements Serializable {
    private Integer id;            //用户id
    @NotBlank(message = "姓名不能不填")
    private String name;           //用户名

    @NotNull(message = "性别不能不填")
    private Byte gender;           //性别

    @NotNull(message = "年龄不能不填")
    @Min(value = 0,message = "年龄必须大于0岁")
    @Max(value = 150,message = "年龄不能大于150岁")
    private Integer age;            //年龄

    @NotBlank(message = "手机号不能不填")
    private String telphone;       //手机号

    private String registerMode;   //注册方式
    private String thirdPartyId;   //第三方Id

    private String encrptPassword; //密文密码

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getRegisterMode() {
        return registerMode;
    }

    public void setRegisterMode(String registerMode) {
        this.registerMode = registerMode;
    }

    public String getThirdPartyId() {
        return thirdPartyId;
    }

    public void setThirdPartyId(String thirdPartyId) {
        this.thirdPartyId = thirdPartyId;
    }

    public String getEncrptPassword() {
        return encrptPassword;
    }

    public void setEncrptPassword(String encrptPassword) {
        this.encrptPassword = encrptPassword;
    }

}
