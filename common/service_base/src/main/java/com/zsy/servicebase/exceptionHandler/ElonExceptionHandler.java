package com.zsy.servicebase.exceptionHandler;

import com.zsy.commonutils.ResModel;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理类
 */
@ControllerAdvice
public class ElonExceptionHandler {

    @ExceptionHandler({Exception.class})
    @ResponseBody
    public ResModel error(Exception e){
        e.printStackTrace();
        return ResModel.error();
    }

    @ExceptionHandler(ElonException.class)
    @ResponseBody
    public ResModel elonError(ElonException e){
        e.printStackTrace();
        return ResModel.error().message(e.getMsg()).code(e.getCode());
    }

}
