package com.zsy.vod.test;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.zsy.servicebase.exceptionHandler.ElonException;
import com.zsy.vod.util.AliyunVodUtils;
import org.junit.Test;

import java.util.List;

/**
 * @ClassName Test
 * @Description 测试类
 * @Author mybook
 * @Date 2020/5/8 8:18 PM
 * @Version 1.0
 */
public class TestVod {

    private static final String ACCESSKEYID = "LTAI4FjU1B1fKiqWHKJPc19q";
    private static final String ACCESSKEYSECRET ="OxUQSJNNS5QMX4Bl7jwXrMJdU0WIOt";
    /**
     * @Author zsy
     * @Description 通过id获取视频的url
     * @Date 8:20 PM 2020/5/8
     * @Param []
     * @return void
     **/
    @Test
    public void urlTest(){
        DefaultAcsClient client = null;
        GetPlayInfoResponse response = null;
        try {
            client = AliyunVodUtils.initVodClient();

            response = AliyunVodUtils.getPlayInfo(client,"83c07e9068a945c590931c87228ea75d");
            List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
            //播放地址
            for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
                System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
            }
        } catch (Exception e) {
            throw new ElonException(20001,"获取课程url错误");
        }
        //Base信息
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");

        System.out.print("RequestId = " + response.getRequestId() + "\n");

    }

    /**
     * @Author zsy
     * @Description 获取播放凭证
     * @Date 8:47 PM 2020/5/8
     * @Param []
     * @return void
     **/
    @Test
    public void authTest(){
        DefaultAcsClient client = null;
        GetVideoPlayAuthResponse response = null;
        try {
            client = AliyunVodUtils.initVodClient();
            response = AliyunVodUtils.getVideoPlayAuth(client, "83c07e9068a945c590931c87228ea75d");
        } catch (Exception e) {
            throw new ElonException(20001,"获取播放凭证失败");
        }
        String playAuth = response.getPlayAuth();
        System.out.println("播放凭证:"+playAuth);
        String title = response.getVideoMeta().getTitle();
        System.out.println("视频title:"+title);

    }
}
