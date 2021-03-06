package com.miaoshaproject.response;

import lombok.Data;

@Data
public class CommonReturnType {
    // 表明对应请求的返回处理结果 "success" 或 "fail"
    private String status;
    // 若status返回success,则data内返回前端需要的json数据
    // 若status = fail，则data内返回错误信息
    private Object data;

    // 定义通用的创建方法
    public static CommonReturnType create(Object result){
        return CommonReturnType.create(result,"success");
    }
    public static CommonReturnType create(Object result,String status){
        CommonReturnType type = new CommonReturnType();
        type.setStatus(status);
        type.setData(result);
        return type;
    }
}
