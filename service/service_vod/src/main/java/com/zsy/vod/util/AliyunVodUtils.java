package com.zsy.vod.util;
import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

/**
 * @ClassName AliyunVodUtils
 * @Description 阿里云视频点播工具类
 * @Author mybook
 * @Date 2020/5/8 8:15 PM
 * @Version 1.0
 */
@Slf4j
@Component
public class AliyunVodUtils implements InitializingBean {
    @Value("${aliyun.vod.accessKeyId}")
    private String keyId;

    @Value("${aliyun.vod.accessKeySecret}")
    private String keySecret;


    public static String KEY_ID;
    public static String KEY_SECRET;


    @Override
    public void afterPropertiesSet() throws Exception {
        KEY_ID = keyId;
        KEY_SECRET = keySecret;
    }
    /**
     * @Author zsy
     * @Description 初始化Vod
     * @Date 8:16 PM 2020/5/8
     * @Param [accessKEY_ID, accessKEY_SECRET]
     * @return com.aliyuncs.DefaultAcsClient
     **/
    public static DefaultAcsClient initVodClient() throws ClientException {
        String regionId = "cn-shanghai";  // 点播服务接入区域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, KEY_ID, KEY_SECRET);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }

    /**
     * @Author zsy
     * @Description 获取通过视频id获取视频响应信息
     * @Date 8:22 PM 2020/5/8
     * @Param [client,vodId]
     * @return com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse
     **/
    public static GetPlayInfoResponse getPlayInfo(DefaultAcsClient client,String vodId) throws Exception {
        if (client == null) {
            client = initVodClient();
        }
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        request.setVideoId(vodId);
        return client.getAcsResponse(request);
    }

    /**
     * @Author zsy
     * @Description 获取视频url 链接但是视频有被盗用的风险
     * @Date 11:10 AM 2020/5/9
     * @Param [vodId]
     * @return java.util.List<com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse.PlayInfo>
     **/
    public static List<GetPlayInfoResponse.PlayInfo>  getPlayUrl(String vodId) throws Exception {
        GetPlayInfoResponse playInfo = getPlayInfo(null, vodId);
        List<GetPlayInfoResponse.PlayInfo> playInfoList = playInfo.getPlayInfoList();
        return playInfoList;
    }

    /**
     * 获取播放凭证
     * @Author zsy
     * @Description 获取播放凭证响应信息
     * @Date 8:48 PM 2020/5/8
     * @Param [client]
     * @return com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse
     **/
    public static GetVideoPlayAuthResponse getVideoPlayAuth(DefaultAcsClient client,String vodId) throws Exception {
        if (client == null) client = initVodClient();
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId(vodId);
        return client.getAcsResponse(request);
    }
    /**
     * @Author zsy
     * @Description 获取播放凭证
     * @Date 11:24 AM 2020/5/9
     * @Param [vodId]
     * @return java.lang.String
     **/
    public static String getPalyAuth(String vodId) throws Exception {
        GetVideoPlayAuthResponse response = getVideoPlayAuth(null, vodId);
        return response.getPlayAuth();
    }

    /**
     * @Author zsy
     * @Description 删除传入的视频
     * @Date 9:10 PM 2020/5/8
     * @Param [client,vodIds]
     * @return com.aliyuncs.vod.model.v20170321.DeleteVideoResponse
     **/
    public static DeleteVideoResponse deleteVideo(DefaultAcsClient client,String vodIds) throws Exception {
        if (client==null) client = initVodClient();
        DeleteVideoRequest request = new DeleteVideoRequest();
        //支持传入多个视频ID，多个用逗号分隔
        request.setVideoIds(vodIds);
        return client.getAcsResponse(request);
    }

    public static UploadVideoResponse uploadVideoByFileName(String title,String fileName){
        //1.音视频上传-本地文件上传
        //视频标题(必选)
        title = "3 - How Does Project Submission Work - upload by sdk";
        //本地文件上传和文件流上传时，文件名称为上传文件绝对路径，如:/User/sample/文件名称.mp4 (必选)
        //文件名必须包含扩展名
        fileName = "E:/共享/资源/课程视频/3 - How Does Project Submission Work.mp4";
        //本地文件上传
        UploadVideoRequest request = new UploadVideoRequest(KEY_ID, KEY_SECRET, title, fileName);
        /* 可指定分片上传时每个分片的大小，默认为1M字节 */
        request.setPartSize(1 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);
    /* 是否开启断点续传, 默认断点续传功能关闭。当网络不稳定或者程序崩溃时，再次发起相同上传请求，可以继续未完成的上传任务，适用于超时3000秒仍不能上传完成的大文件。
        注意: 断点续传开启后，会在上传过程中将上传位置写入本地磁盘文件，影响文件上传速度，请您根据实际情况选择是否开启*/
        request.setEnableCheckpoint(false);
        UploadVideoImpl uploader = new UploadVideoImpl();
        return uploader.uploadVideo(request);
    }

    public static UploadStreamResponse  uploadVideoByStream( String title, String fileName, InputStream inputStream) {
        UploadStreamRequest request = new UploadStreamRequest(KEY_ID, KEY_SECRET, title, fileName, inputStream);
        /* 是否使用默认水印(可选)，指定模板组ID时，根据模板组配置确定是否使用默认水印*/
        //request.setShowWaterMark(true);
        /* 设置上传完成后的回调URL(可选)，建议通过点播控制台配置消息监听事件，参见文档 https://help.aliyun.com/document_detail/57029.html */
        //request.setCallback("http://callback.sample.com");
        /* 自定义消息回调设置，参数说明参考文档 https://help.aliyun.com/document_detail/86952.html#UserData */
        //request.setUserData(""{\"Extend\":{\"test\":\"www\",\"localId\":\"xxxx\"},\"MessageCallback\":{\"CallbackURL\":\"http://test.test.com\"}}"");
        /* 视频分类ID(可选) */
        //request.setCateId(0);
        /* 视频标签,多个用逗号分隔(可选) */
        //request.setTags("标签1,标签2");
        /* 视频描述(可选) */
        //request.setDescription("视频描述");
        /* 封面图片(可选) */
        //request.setCoverURL("http://cover.sample.com/sample.jpg");
        /* 模板组ID(可选) */
        //request.setTemplateGroupId("8c4792cbc8694e7084fd5330e56a33d");
        /* 工作流ID(可选) */
        //request.setWorkflowId("d4430d07361f0*be1339577859b0177b");
        /* 存储区域(可选) */
        //request.setStorageLocation("in-201703232118266-5sejdln9o.oss-cn-shanghai.aliyuncs.com");
        /* 开启默认上传进度回调 */
        // request.setPrintProgress(true);
        /* 设置自定义上传进度回调 (必须继承 VoDProgressListener) */
        // request.setProgressListener(new PutObjectProgressListener());
        /* 设置应用ID*/
        //request.setAppId("app-1000000");
        /* 点播服务接入点 */
        //request.setApiRegionId("cn-shanghai");
        /* ECS部署区域*/
        // request.setEcsRegionId("cn-shanghai");
        UploadVideoImpl uploader = new UploadVideoImpl();

        return uploader.uploadStream(request);
//        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
//        if (response.isSuccess()) {
//            System.out.print("VideoId=" + response.getVideoId() + "\n");
//        } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
//            System.out.print("VideoId=" + response.getVideoId() + "\n");
//            System.out.print("ErrorCode=" + response.getCode() + "\n");
//            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
//        }
    }


}
