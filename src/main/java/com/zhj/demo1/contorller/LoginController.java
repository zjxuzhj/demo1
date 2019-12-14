package com.zhj.demo1.contorller;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.zhj.demo1.model.LoveResult;
import okhttp3.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Objects;

@RestController
public class LoginController {
    public final static String X_BMOB_APPLICATION_ID = "92b20b26c6cd96638faeea2ebc309b83";
    public final static String X_BMOB_REST_API_KEY = "cd35bb1567753c81b61d6e9d4a285502";
    public final static String LoveStagesTemp_URL = "https://api2.bmob.cn/1/classes/LoveStagesTemp";
    public final static String LoveStages_URL = "https://api2.bmob.cn/1/classes/LoveStages";

    /**
     * 用于接口调用或循环任务自动执行每天添加两篇文章
     *
     * @return 传入的参数暂时无用，反参也无用
     */
    @RequestMapping("/getLoveStagesTemp")
    public void getLoveStagesTemp() {
        //此处将要发送的数据转换为json格式字符串
        String jsonText = "{id:1}";
        JSONObject json = (JSONObject) JSONObject.parse(jsonText);
        //该方法用于获得临时文章表中的数据
        JSONObject sr = firstGetTemp(json);
        if (sr.getJSONArray("results").size() > 0) {
            JSONObject loveStage = (JSONObject) sr.getJSONArray("results").get(0);
            loveStage.remove("createdAt");
            loveStage.remove("objectId");
            loveStage.remove("updatedAt");
            Integer id = loveStage.getInteger("id");
            try {
                if (getSameSize(id) == 0) {
                    addNewLoveStages(loveStage);
                } else {
                    System.out.println("当前文章已存在，请勿重复添加");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("当前无可用文章");
        }
    }

    /**
     * 该方法用于添加新的文章
     *
     * @param tempJson 需要添加的文章对象
     * @return
     */
    public void addNewLoveStages(JSONObject tempJson) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(LoveStages_URL))
                .newBuilder();
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), tempJson.toJSONString());
        Request req = new Request.Builder()
                .url(urlBuilder.build())
                .addHeader("content-type", "application/json")
                .addHeader("X-Bmob-Application-Id", X_BMOB_APPLICATION_ID)
                .addHeader("X-Bmob-REST-API-Key", X_BMOB_REST_API_KEY)
                .post(requestBody)
                .build();
        Response rep = okHttpClient.newCall(req).execute();
        System.out.println("addNewLoveStages 返回码：" + rep.code());
        System.out.println("addNewLoveStages 返回内容：" + rep.body().string());
    }

    /**
     * 该方法用于获得临时文章表中的数据
     *
     * @param id
     * @return
     */
    public int getSameSize(Integer id) throws IOException {
        OkHttpClient okHttpClient = new OkHttpClient();
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(LoveStages_URL))
                .newBuilder();
        urlBuilder.addQueryParameter("where", "{\"id\":" + id + "}")
                .addQueryParameter("count", "1")
                .addQueryParameter("limit", "0");
        Request req = new Request.Builder()
                .url(urlBuilder.build())
                .addHeader("content-type", "application/json")
                .addHeader("X-Bmob-Application-Id", X_BMOB_APPLICATION_ID)
                .addHeader("X-Bmob-REST-API-Key", X_BMOB_REST_API_KEY)
                .build();
        Response rep = okHttpClient.newCall(req).execute();
        String returnString = rep.body().string();
        System.out.println("getSameSize 返回码：" + rep.code());
        System.out.println("getSameSize 返回内容：" + returnString);
        LoveResult loveResult = new Gson().fromJson(returnString, LoveResult.class);
        return loveResult.getCount();
    }

    /**
     * 该方法用于获得临时文章表中的数据
     *
     * @param date
     * @return
     */
    public JSONObject firstGetTemp(JSONObject date) {
        // 要调用的接口方法
        JSONObject jsonObject;

        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(LoveStagesTemp_URL))
                    .newBuilder();
            urlBuilder.addQueryParameter("order", "createdAt");
            Request req = new Request.Builder()
                    .url(urlBuilder.build())
                    .addHeader("content-type", "application/json")
                    .addHeader("X-Bmob-Application-Id", X_BMOB_APPLICATION_ID)
                    .addHeader("X-Bmob-REST-API-Key", X_BMOB_REST_API_KEY)
                    .build();
            Response rep = okHttpClient.newCall(req).execute();
            String returnString = rep.body().string();
            System.out.println("firstGetTemp 返回码：" + rep.code());
            System.out.println("firstGetTemp 返回内容：" + returnString);
            jsonObject = JSONObject.parseObject(returnString);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return jsonObject;
    }

    //    @GetMapping("/selectUser")
//    @RequestMapping("/selectUser")
//    public HashMap selectUser(String name) {
//        return loginService.selectUser(name);
//    }


}


