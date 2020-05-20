package com.zsy.crm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsy.commonutils.ResModel;
import com.zsy.crm.entity.Banner;
import com.zsy.crm.service.BannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName BannerAdminController
 * @Description 负责后台管理的接口
 * @Author mybook
 * @Date 2020/5/16 4:42 PM
 * @Version 1.0
 */
@Api(description = "后台管理轮播图")
@RestController
@RequestMapping("/crm/banner/admin")
public class BannerAdminController {

    @Autowired
    private BannerService bannerService;
    // id查询
    @GetMapping("{id}")
    @ApiOperation("根据轮播图Id查询轮播图信息")
    public ResModel getBanner(@ApiParam("轮播图ID") @PathVariable String id){
        return ResModel.success().data("data",bannerService.getById(id));
    }
    // 分页查询
    @PostMapping("findBannerPage/{curNum}/{size}")
    @ApiOperation("分页查询轮播图信息")
    public ResModel findBannerPage(@PathVariable Integer curNum , @PathVariable Integer size){
        // TODO Banner可以完善为分页条件查询
        Page<Banner> page = new Page<>();
        page.setSize(size);
        page.setCurrent(curNum);
        page.setAsc("sort");
        return ResModel.success().data("data",bannerService.page(page,null));
    }
    // 新增
    @PostMapping("save")
    @ApiOperation("新增轮播图信息")
    public ResModel saveBanner(@ApiParam(name = "轮播图信息",required = true)@RequestParam Banner banner){
        boolean flag = bannerService.save(banner);
        if (flag) return ResModel.success();
        return ResModel.error();

    }
    // 修改
    @PostMapping("update")
    @ApiOperation("修改轮播图信息")
    public ResModel updateBanner(@ApiParam(name="修改后轮播图信息",required = true) Banner banner){
        boolean flag = bannerService.updateById(banner);
        if (flag) return ResModel.success();
        return ResModel.error();
    }

    // 删除
    @DeleteMapping("{id}")
    @ApiOperation("根据轮播信息ID删除轮播信息")
    public ResModel deleteBanner(@PathVariable String id){
        boolean flag = bannerService.removeById(id);
        if (flag) return ResModel.success();
        return ResModel.error();
    }
}



