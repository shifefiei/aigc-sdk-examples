package com.aigc.sdk.examples.test.v1;

import com.aigc.sdk.examples.bean.v1.avatar.*;
import com.aigc.sdk.examples.bean.v1.voice.UploadFileResponse;
import com.aigc.sdk.examples.common.CommonConstant;
import com.aigc.sdk.examples.config.OkHttpConfig;
import com.aigc.sdk.examples.util.FileUtil;
import com.aigc.sdk.examples.util.OkHttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 数字人形象相关测试
 */
public class MainAvatarDemoTest {

    private static Logger log = LoggerFactory.getLogger(MainAvatarDemoTest.class);

    private static OkHttpUtil okHttpUtil = new OkHttpUtil(OkHttpConfig.getClient());

    private static String avatarListUrl = CommonConstant.HOST + "/apis/digitalhuman/avatar/v1/list";

    private static String uploadAvatarUrl = CommonConstant.HOST + "/apis/digitalhuman/avatar/v1/uploadAvatar";

    private static String getUploadCustomAvatarUrl = CommonConstant.HOST + "/apis/digitalhuman/avatar/v1/getUploadCustomAvatar";

    private static String getDigitalHumanMsgUrl = CommonConstant.HOST + "/apis/digitalhuman/avatar/v1/getDigitalHumanMsg";


    /**
     * 获取数字人形象列表
     */
    @Test
    public void testAvatarList() {
        DigitalHumanAvatarListRequest req = new DigitalHumanAvatarListRequest();
        req.setSupportTypeId(101);
        req.setDigitalHumanId(5);

        // req.setAreaTypeId(140);
        //req.setTagIds(Lists.newArrayList(211));

        List<DigitalHumanAvatarListResponse> avatarList = getAvatarList(req);
        System.out.println("==========================");
        System.out.println(avatarList);
    }

    /**
     * 获取数字人形象列表
     */
    @Test
    public void testAvatarListV1() {
        DigitalHumanAvatarListResponse avatarList = getAvatarListV1(100, 397);
        System.out.println(avatarList);
    }

    /**
     * 只进行图片上传
     */
    @Test
    public void testUploadAvatarFileV1() {
        String fileUrl = "https://creative-aigc-test.s3.ap-southeast-1.amazonaws.com/test/lin.jpg";
        UploadFileResponse response = getUploadAvatarFileV1(fileUrl);
        System.out.println(response);
    }

    /**
     * 图片上传异步查询上传结果
     */
    @Test
    public void testGetUploadAvatarStatusV1() {
        String fileUrl = "https://creative-aigc-test.s3.ap-southeast-1.amazonaws.com/test/lin.jpg";
        //UploadFileResponse response = getUploadAvatarFileV1(fileUrl);
        Integer uploadAvatarStatus = getUploadAvatarStatusV1(1132522);

        //  PROCESSING("0", "处理中"),SUCCESS("1", "处理成功"), FAIL("2", "处理失败"),TIMEOUT("3", "处理超时");
        System.out.println(uploadAvatarStatus);
    }


    /**
     * 测试上传照片并查询照片ID和数字人ID的关系
     */
    @Test
    public void testGetUploadAvatarAndDigitalHumanId() {
        String fileUrl = "https://creative-aigc-test.s3.ap-southeast-1.amazonaws.com/test/api-1.jpg";
        UploadFileResponse response = getUploadAvatarFileV1(fileUrl);
        Integer uploadAvatarStatus = getUploadAvatarStatusV1(response.getFileId());
        while (true) {
            uploadAvatarStatus = getUploadAvatarStatusV1(response.getFileId());
            if (uploadAvatarStatus == 1) {
                break;
            }
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        DigitalHumanMsgResponse photoDigital = getPhotoDigitalHumanId(response.getFileId());
        System.out.println(photoDigital);
    }

    /**
     * 查询照片数字人关系
     */
    public DigitalHumanMsgResponse getPhotoDigitalHumanId(Integer fileId) {
        DigitalHumanMsgRequest request = new DigitalHumanMsgRequest();
        request.setSupportTypeId(100);
        request.setFileId(fileId);

        String digitalHumanMsgResult = okHttpUtil.doPostJson(getDigitalHumanMsgUrl, JSON.toJSONString(request));
        JSONObject jsonObject = JSONObject.parseObject(digitalHumanMsgResult);
        if (!"200".equals(jsonObject.getString("code"))) {
            log.error(" getPhotoDigitalHumanId error , msg = {} ", digitalHumanMsgResult);
        }

        log.info("照片和数字人ID关系 = {}", digitalHumanMsgResult);

        DigitalHumanMsgResponse response = JSONObject.parseObject(jsonObject.getString("data"), DigitalHumanMsgResponse.class);
        return response;
    }


    public Integer getUploadAvatarStatusV1(Integer fileId) {

        Map<String, String> map = new HashMap<>();
        map.put("fileId", fileId + "");

        String resultData = okHttpUtil.doPostJson(getUploadCustomAvatarUrl, JSON.toJSONString(map));
        JSONObject jsonObject = JSONObject.parseObject(resultData);

        if (!"200".equals(jsonObject.getString("code"))) {
            log.error(" getUploadAvatarStatusV1 error , msg = {} ", resultData);
            return null;
        }

        log.info("照片上传结果查询 , resultData = {}", resultData);

        String data = jsonObject.getString("data");

        FacedetectionStatusResp facedetectionStatusResp = JSONObject.parseObject(data, FacedetectionStatusResp.class);
        System.out.println("FacedetectionStatusResp result : " + JSONObject.toJSONString(facedetectionStatusResp));
        return facedetectionStatusResp.getFileId();
    }

    public List<DigitalHumanAvatarListResponse> getAvatarList(DigitalHumanAvatarListRequest request) {

        String avatarResultList = okHttpUtil.doPostJson(avatarListUrl, JSONObject.toJSONString(request));
        JSONObject jsonObject = JSONObject.parseObject(avatarResultList);
        if (!"200".equals(jsonObject.getString("code"))) {
            log.error(" uploadCustomVoiceV1 error , msg = {} ", avatarResultList);
            return null;
        }

        String pageData = jsonObject.getString("data");

        System.out.println("接口返回的字符串：" + pageData);

        JSONObject pageJsonObject = JSONObject.parseObject(pageData);

        List<DigitalHumanAvatarListResponse> avatarList = JSONObject.parseArray(pageJsonObject.getString("data"), DigitalHumanAvatarListResponse.class);

        return avatarList;
    }

    public DigitalHumanAvatarListResponse getAvatarListV1(Integer supportTypeId, Integer humanId) {

        DigitalHumanAvatarListRequest request = new DigitalHumanAvatarListRequest();
        request.setDigitalHumanId(humanId);
        request.setSupportTypeId(supportTypeId);


        String avatarResultList = okHttpUtil.doPostJson(avatarListUrl, JSONObject.toJSONString(request));
        JSONObject jsonObject = JSONObject.parseObject(avatarResultList);
        if (!"200".equals(jsonObject.getString("code"))) {
            log.error(" uploadCustomVoiceV1 error , msg = {} ", avatarResultList);
            return null;
        }

        String pageData = jsonObject.getString("data");
        JSONObject pageJsonObject = JSONObject.parseObject(pageData);

        List<DigitalHumanAvatarListResponse> avatarList = JSONObject.parseArray(pageJsonObject.getString("data"), DigitalHumanAvatarListResponse.class);
        List<DigitalHumanAvatarListResponse> collect = avatarList.stream().filter(e -> e.getDigitalHumanId().equals(humanId)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(collect)) {
            throw new RuntimeException("请检查ID=" + humanId + "的数字人是否存在 ！");
        }
        return collect.get(0);
    }


    public UploadFileResponse getUploadAvatarFileV1(String fileUrl) {

        File file = FileUtil.urlToMultipartFile(fileUrl, "407-1");

        String originalFilename = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        String uploadFileData = okHttpUtil.doUploadFile(uploadAvatarUrl, file, originalFilename);

        JSONObject auditionJsonObject = JSONObject.parseObject(uploadFileData);
        if (!"200".equals(auditionJsonObject.getString("code"))) {
            log.error(" uploadCustomVoiceV1 error , msg = {} ", uploadFileData);
            return null;
        }

        log.info("getUploadAvatarFileV1 result : {}", uploadFileData);

        UploadFileResponse response = JSON.parseObject(auditionJsonObject.getString("data"), UploadFileResponse.class);
        return response;
    }

}
