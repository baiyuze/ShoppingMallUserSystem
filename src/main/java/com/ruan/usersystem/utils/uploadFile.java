package com.ruan.usersystem.utils;
import java.io.*;
import java.util.Date;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectRequest;
import org.springframework.web.multipart.MultipartFile;

public class uploadFile {
    // Endpoint以杭州为例，其它Region请按实际情况填写。oss-cn-beijing-internal.aliyuncs.com
    String endpoint = "https://oss-cn-beijing.aliyuncs.com";

    // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
    String accessKeyId = "LTAI4FzBeWzW7Ged5c9UzeMW";
    String accessKeySecret = "aICivZiLvrxBX8woyFHRc1wrs1NLyw";
    String bucketName = "cake-shoping";

    // 创建OSSClient实例。
    public String start(String fileName, MultipartFile file) throws Exception {
        // 创建PutObjectRequest对象。
        // <yourObjectName>表示上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 上传文件流。
        InputStream inputStream = file.getInputStream();
        ossClient.putObject(bucketName, fileName, inputStream);
        // 关闭OSSClient。
        ossClient.shutdown();
        Date expiration = new Date(new Date().getTime() + 3600 * 1000 * 24 * 365 * 10);
        String url = ossClient.generatePresignedUrl(bucketName, fileName, expiration).toString();
        // 创建OSSClient实例。
        return url;
    }

}
