package com.zsy.edu.client.impl;

import com.zsy.commonutils.ResModel;
import com.zsy.edu.client.VodClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName VodClientImpl
 * @Description TODO
 * @Author mybook
 * @Date 2020/5/15 4:52 PM
 * @Version 1.0
 */

@Component
@Slf4j
public class VodClientImpl implements VodClient{

    @Override
    public ResModel removeVideo(String videoId) {
        log.info("保存数据库");
        return ResModel.error();
    }

    @Override
    public ResModel deleteByVodIdList(List<String> vodIds) {
        log.info("保存数据库");
        return ResModel.error();
    }
}
