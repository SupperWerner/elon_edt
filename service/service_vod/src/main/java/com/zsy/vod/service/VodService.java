package com.zsy.vod.service;

import com.zsy.commonutils.ResModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List; /**
 * @ClassName VodService
 * @Description TODO
 * @Author mybook
 * @Date 2020/5/9 12:23 PM
 * @Version 1.0
 */

public interface VodService {
    ResModel uploadVideoAly(MultipartFile file);

    ResModel myRemoveVideo(String videoId);

    ResModel myDeleteByVodIdList(List<String> vodIds);
}
