package com.zsy.vod.service;

import org.springframework.web.multipart.MultipartFile; /**
 * @ClassName VodService
 * @Description TODO
 * @Author mybook
 * @Date 2020/5/9 12:23 PM
 * @Version 1.0
 */

public interface VodService {
    String uploadVideoAly(MultipartFile file);
}
