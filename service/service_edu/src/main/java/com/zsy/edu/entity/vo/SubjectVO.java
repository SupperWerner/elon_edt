package com.zsy.edu.entity.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SubjectVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String label;
    private List<SubjectVO> children;

}
