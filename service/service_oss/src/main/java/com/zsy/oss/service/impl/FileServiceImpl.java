package com.zsy.oss.service.impl;

import com.aliyun.oss.OSS;

import com.aliyun.oss.model.CannedAccessControlList;
import com.zsy.oss.service.FileService;
import com.zsy.oss.util.ConstantPropertiesUtil;
import com.zsy.oss.util.OssServiceUtil;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Override
    public String upload(MultipartFile file) {
        OSS ossClient = OssServiceUtil.OSER.createOssClient(); // 获取对象
        String endpoint = ConstantPropertiesUtil.END_POINT; // 获取endPoint
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;// 获取Bucket名称

        InputStream fileInputStream = null;
        try {
            // 判断BucketName对应的实例是否存在
            if (!ossClient.doesBucketExist(bucketName)){
                // 如果不存在则创建新的bucket实例
                ossClient.createBucket(bucketName);

                // 设置 公共读权限
                ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
            }

            fileInputStream = file.getInputStream(); // 获取文件流

            // 设置文件按日期保存
            String filePath = new DateTime().toString("yyyy/MM/dd");

            String fileName = file.getOriginalFilename();// 获取上传文件名
            // 获取唯一文件名
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            fileName = filePath+"/"+uuid+fileName.substring(fileName.indexOf("."));
            log.info("保存后的路径为: {}",fileName);
            // 保存到Oss
            ossClient.putObject(bucketName,fileName, fileInputStream);

            // 关闭OSSClient。
            ossClient.shutdown();

            // 保存成功后Oss并不会返回访问时的字符串 所以该方法我们需要拼接字符串进行返回  拼接格式如下:
            // https://elon-edu.oss-cn-beijing.aliyuncs.com/6.png
            return "https://"+ bucketName +"."+ endpoint +"/"+fileName ;
        } catch (IOException e) {
            return null;
        }
    }
}
