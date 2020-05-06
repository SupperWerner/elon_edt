package com.zsy.edu.service;

import com.zsy.commonutils.ResModel;
import com.zsy.edu.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author zsy
 * @since 2020-04-28
 */
public interface ChapterService extends IService<Chapter> {

    ResModel myGetChapterAndVideo(String id);

    boolean myRemoveById(String id);
}
