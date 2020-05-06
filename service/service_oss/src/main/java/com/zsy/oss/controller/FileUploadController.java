package com.zsy.oss.controller;

import com.zsy.commonutils.ResModel;
import com.zsy.oss.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Api(description="阿里云文件管理")
@CrossOrigin //跨域
@RestController
@RequestMapping("/oss/file")
public class FileUploadController {
    @Autowired
    private FileService fileService;

    @ApiOperation(value="上传文件")
    @PostMapping("upload")
    public ResModel upload(
            @ApiParam(name="file" , value = "文件" ,required = true)
            @RequestParam("file")MultipartFile file){
            String url = fileService.upload(file);
            if (StringUtils.isNotEmpty(url)){
                return ResModel.success().data("url",url);
            }
        return ResModel.error();
    }
}
