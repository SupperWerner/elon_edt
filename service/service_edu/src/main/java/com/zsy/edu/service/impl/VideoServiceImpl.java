package com.zsy.edu.service.impl;

import com.zsy.edu.entity.Video;
import com.zsy.edu.mapper.VideoMapper;
import com.zsy.edu.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2020-04-28
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

}
