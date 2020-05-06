package com.zsy.servicebase.exceptionHandler;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ElonException extends RuntimeException {
    @ApiModelProperty(value="状态码")
    private Integer code;

    @ApiModelProperty(value = "状态信息")
    private String msg;

}
