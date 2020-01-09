package com.zhj.demo1.contorller;

import com.google.gson.Gson;
import com.zhj.demo1.api.BaseInterceptor;
import com.zhj.demo1.model.LoveHealing;
import com.zhj.demo1.model.LoveHealingResult;
import okhttp3.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * 实战情话，每日添加两段
 */
@RestController
public class LoveHealingController {
    public final static String LoveStagesTemp_URL = "https://api2.bmob.cn/1/classes/LoveStagesTemp";
    public final static String LoveHealing_URL = "https://api2.bmob.cn/1/classes/LoveHealing";

    /**
     * 实战情话，每日添加两段
     */
    @RequestMapping("/addLoveHealing")
    public void addLoveHealing() {
        // 要调用的接口方法
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new BaseInterceptor()).build();
        try {
            HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(LoveHealing_URL))
                    .newBuilder();
            urlBuilder.addQueryParameter("where", "{\"isShow\":false}")
                    .addQueryParameter("order", "createdAt")
                    .addQueryParameter("limit", "2");
            Request req = new Request.Builder()
                    .url(urlBuilder.build())
                    .build();
            Response rep = okHttpClient.newCall(req).execute();
            String returnString = rep.body().string();
            System.out.println("查询实战情话 返回码：" + rep.code());
            System.out.println("查询实战情话 返回内容：" + returnString);
            LoveHealingResult loveHealingResult = new Gson().fromJson(returnString, LoveHealingResult.class);
            if (loveHealingResult.getResults().size() == 0) {
                System.out.println("实战情话库存为0");
            } else {
                for (LoveHealing result : loveHealingResult.getResults()) {
                    urlBuilder = Objects.requireNonNull(HttpUrl.parse(LoveHealing_URL + "/" + result.objectId))
                            .newBuilder();
                    RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), "{\"isShow\":true}");
                    req = new Request.Builder().url(urlBuilder.build()).put(requestBody).build();
                    rep = okHttpClient.newCall(req).execute();
                    System.out.println("实战表修改状态 返回码：" + rep.code());
                    System.out.println("实战表修改状态 返回内容：" + rep.body().string());
                }

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}


