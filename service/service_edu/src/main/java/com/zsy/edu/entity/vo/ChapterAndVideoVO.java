package com.zsy.edu.entity.vo;

import com.zsy.edu.entity.Video;
import lombok.Data;

import java.util.List;

@Data
public class ChapterAndVideoVO {
    private String id;
    private String title;
    private List<Video> children;
}
