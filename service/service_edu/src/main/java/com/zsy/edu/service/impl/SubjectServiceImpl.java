package com.zsy.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.zsy.commonutils.ResModel;
import com.zsy.edu.entity.Subject;
import com.zsy.edu.entity.excel.ExcelSubjectData;
import com.zsy.edu.listener.SubjectListener;
import com.zsy.edu.mapper.SubjectMapper;
import com.zsy.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsy.servicebase.exceptionHandler.ElonException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author zsy
 * @since 2020-04-16
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Override
    public ResModel exportExcel(MultipartFile file, SubjectService subjectService) {
        try{
            EasyExcel.read(file.getInputStream(), ExcelSubjectData.class,new SubjectListener(subjectService)).sheet().doRead();
        }catch(Exception e){
            e.printStackTrace();
            throw new ElonException(20001,"解析失败");
        }
        return ResModel.success();
    }


}
