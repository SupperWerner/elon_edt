package com.zsy.oss.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

public enum  OssServiceUtil {
    OSER;
    public OSS createOssClient(){
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtil.END_POINT;
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = ConstantPropertiesUtil.KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.KEY_SECRET;

        // 创建对象并返回
        return new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }
}
