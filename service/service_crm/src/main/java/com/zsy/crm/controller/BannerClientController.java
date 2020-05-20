package com.zsy.crm.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zsy.commonutils.ResModel;
import com.zsy.crm.entity.Banner;
import com.zsy.crm.service.BannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author zsy
 * @since 2020-05-16
 */
@Api(description = "前台管理轮播图")
@RestController
@RequestMapping("/crm/banner/client")
@CrossOrigin
public class BannerClientController {

    @Autowired
    private BannerService bannerService;

    @GetMapping
    @Cacheable(value="banner",key="#root.methodName")
    @ApiOperation("获取展示数据")
    public ResModel getBanner(){
        QueryWrapper<Banner> wrapper = new QueryWrapper<>();
        wrapper.orderByAsc("sort");
        return ResModel.success().data("data",bannerService.list(wrapper));
    }

}

