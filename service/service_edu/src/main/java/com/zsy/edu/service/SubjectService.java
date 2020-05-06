package com.zsy.edu.service;

import com.zsy.commonutils.ResModel;
import com.zsy.edu.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author zsy
 * @since 2020-04-16
 */
public interface SubjectService extends IService<Subject> {

    ResModel exportExcel(MultipartFile file, SubjectService subjectService);
}
