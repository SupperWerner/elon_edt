package com.zsy.edu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zsy.edu.entity.Subject;
import com.zsy.edu.entity.excel.ExcelSubjectData;
import com.zsy.edu.service.SubjectService;
import com.zsy.servicebase.exceptionHandler.ElonException;

import java.util.Date;

public class SubjectListener extends AnalysisEventListener<ExcelSubjectData> {
    private SubjectService subjectService;

    public SubjectListener(){}
    public SubjectListener(SubjectService subjectService){
        this.subjectService = subjectService;
    }

    @Override
    public void invoke(ExcelSubjectData data, AnalysisContext analysisContext) {
        if (data == null){
            throw new ElonException(20001,"Excel没有数据!");
        }

        // 判断是否已经存在于数据库中
        Subject subject = existSubjectName(data.getOneSubjectName(),"0");
        if (subject==null) {
            // 数据库中没有一级菜单则新增
            Subject oneSubject = saveSubject(data.getOneSubjectName(), "0", 0); // 返回新增的一级课程

            // 获取二级课程菜单信息
            Subject existSubject = existSubjectName(data.getTwoSubjectName(), oneSubject.getParentId());

            // 判断是否为空
            if (existSubject==null){
                // 为空则新增
                saveSubject(data.getTwoSubjectName(), oneSubject.getParentId(), 0);
            }

        } else {
            // 有则进行二级菜单的判断
            Subject twoSubject = existSubjectName(data.getTwoSubjectName(), subject.getId());
            if (twoSubject == null) {
                // 数据库中没有二级课程则进行新增
                saveSubject(data.getTwoSubjectName(), subject.getId(), 0);
            }
        }


    }


    /**
     * 新增课程 / 并返回新增后的课程信息
     * @param title
     * @param parentId
     * @param sort
     * @return
     */
    private Subject saveSubject(String title,String parentId,Integer sort){
        Subject saveSubject = new Subject();
        saveSubject.setParentId(parentId);
        saveSubject.setTitle(title);
        saveSubject.setSort(sort);
        saveSubject.setGmtCreate(new Date());
        saveSubject.setGmtModified(new Date());
        boolean saveOneFlag = subjectService.save(saveSubject);
        if (saveOneFlag) {
            return saveSubject;
        } else {
            throw new ElonException(20001,"持久化数据错误!");
        }
    }



    /**
     * 判断是否已经存在于数据库中
     * @param subjectName
     * @return
     */
    private Subject existSubjectName(String subjectName,String parentId){
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",subjectName);
        wrapper.eq("parent_id",parentId);
        return  subjectService.getOne(wrapper);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
