package com.zsy.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zsy.commonutils.ResModel;
import com.zsy.edu.entity.Chapter;
import com.zsy.edu.entity.Video;
import com.zsy.edu.entity.vo.ChapterAndVideoVO;
import com.zsy.edu.mapper.ChapterMapper;
import com.zsy.edu.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsy.edu.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2020-04-28
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Autowired
    private VideoService videoService;

    @Override
    public ResModel myGetChapterAndVideo(String id) {

        /*
            对应id的所有的课程章节信息
         */
        QueryWrapper<Chapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",id);
        wrapper.select("id","title");
        wrapper.orderByAsc("sort");
        List<Chapter> chapters = this.baseMapper.selectList(wrapper);


        /*
            获取课程id对应的小结信息
         */
        QueryWrapper<Video> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("course_id",id);
        videoWrapper.orderByAsc("sort");
        List<Video> videos = videoService.list(videoWrapper);
        List<ChapterAndVideoVO> chapterAndVideos = getChapterAndVideo(chapters, videos);

        return ResModel.success().data("chapterAndVideos",chapterAndVideos);
    }

    /**
     * 传入课程信息章节信息 和 小结信息
     * 获取ChapterAndVideoVO 对象
     * @param chapters
     * @param videos
     * @return
     */
    private List<ChapterAndVideoVO> getChapterAndVideo(List<Chapter> chapters,List<Video> videos){
        List<ChapterAndVideoVO> cvVOs = new ArrayList<>();
        chapters.forEach(chapter -> {  // 遍历章节信息
            ChapterAndVideoVO cav = new ChapterAndVideoVO();
            cav.setId(chapter.getId());
            cav.setTitle(chapter.getTitle());
            cav.setChildren(videos
                                .stream()
                                .filter(video -> Objects.equals(chapter.getId(), video.getChapterId()))
                                .collect(Collectors.toList()));
            cvVOs.add(cav);
        });
        return cvVOs;
    }

    /**
     * @Author zsy
     * @Description 根据删除视频信息与课程信息
     * @Date 1:26 PM 2020/5/7
     * @Param [id] chapterId 章节id
     * @return boolean
     **/
    @Override
    public boolean myRemoveById(String id) {
        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",id);
        boolean rmVideo = videoService.remove(wrapper);
        int rmChapter = this.baseMapper.deleteById(id);
        return rmChapter>0&&rmVideo;
    }

    /**
     * @Author zsy
     * @Description 根据courseId删除课程 章节与视频信息
     * @Date 3:08 PM 2020/5/7
     * @Param [id]
     * @return boolean
     **/
    @Override
    public boolean myRemoveByCourseId(String id) {
        /* 组装video删除条件 并删除*/
        QueryWrapper<Video> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("course_id",id);
        boolean removeVideo = videoService.remove(videoWrapper);

        /* 组装chapter删除条件 并删除*/
        QueryWrapper<Chapter> chapterWrapper = new QueryWrapper<>();
        chapterWrapper.eq("course_id",id);
        int deleteChapter = this.baseMapper.delete(chapterWrapper);
        return removeVideo && deleteChapter>0;
    }
}
