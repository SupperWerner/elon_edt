package com.zsy.commonutils;

import io.swagger.annotations.ApiKeyAuthDefinition;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class ResModel {
    @ApiModelProperty(value = "状态码")
    private Integer code;
    @ApiModelProperty(value = "是否成功")
    private boolean success;
    @ApiModelProperty(value = "提示信息")
    private String message;
    @ApiModelProperty(value = "响应数据")
    private Map<String,Object> data;

    private ResModel() {}

    public static ResModel success(){
        ResModel res = new ResModel();
        res.setCode(ResultCode.SUCCESS);
        res.setSuccess(true);
        res.setMessage("成功");
        return res;
    }

    public static ResModel error(){
        ResModel res = new ResModel();
        res.setCode(ResultCode.ERROR);
        res.setSuccess(false);
        res.setMessage("失败");
        return res;
    }

    public ResModel message(String msg){
        this.setMessage(msg);
        return this;
    }

    public ResModel code(Integer code){
        this.setCode(code);
        return this;
    }

    public ResModel isOk(boolean flag){
        this.setSuccess(flag);
        return this;
    }

    public ResModel data(Map<String,Object> map){
        this.setData(map);
        return this;
    }

    public ResModel data(String key,Object obj){
        Map<String,Object> map = new HashMap<>();
        map.put(key,obj);
        this.setData(map);
        return this;
    }

}
