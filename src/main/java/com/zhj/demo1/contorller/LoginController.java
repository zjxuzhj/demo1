package com.zhj.demo1.contorller;

import com.alibaba.fastjson.JSONObject;
import com.zhj.demo1.model.User;
import com.zhj.demo1.service.LoginService;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Objects;

@RestController
public class LoginController {
    public final static String X_BMOB_APPLICATION_ID = "92b20b26c6cd96638faeea2ebc309b83";
    public final static String X_BMOB_REST_API_KEY = "cd35bb1567753c81b61d6e9d4a285502";
    @Autowired
    private LoginService loginService;

    public static JSONObject firstGetTemp(JSONObject date) {
        // 要调用的接口方法
        String url = "https://api2.bmob.cn/1/classes/LoveStagesTemp";
        JSONObject jsonObject;

        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            //post 添加 参数
//            RequestBody requestBody = new FormBody.Builder()
//                    .add("order", "createdAt")
//                    .build();
            HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(url))
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
            System.out.println("返回码：" + rep.code());
            System.out.println("返回内容：" + returnString);
            jsonObject = JSONObject.parseObject(returnString);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return jsonObject;
    }

    @PostMapping("/register")
    public HashMap register(User userVo) {
        return loginService.insertSelective(userVo);
    }

    //    @GetMapping("/selectUser")
    @RequestMapping("/selectUser")
    public HashMap selectUser(String name) {
        return loginService.selectUser(name);
    }

    @RequestMapping("/getLoveStagesTemp")
    public JSONObject getLoveStagesTemp() throws ParseException {
        //此处将要发送的数据转换为json格式字符串
        String jsonText = "{id:1}";
        JSONObject json = (JSONObject) JSONObject.parse(jsonText);
        JSONObject sr = firstGetTemp(json);
        Integer id = sr.getInteger("id");
        String url = "https://api2.bmob.cn/1/classes/LoveStages";
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            //post 添加 参数
//            RequestBody requestBody = new FormBody.Builder()
//                    .add("order", "createdAt")
//                    .build();
            HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(url))
                    .newBuilder();
            urlBuilder.addQueryParameter("where", "{\"id\":" + id + "}");
            Request req = new Request.Builder()
                    .url(urlBuilder.build())
                    .addHeader("content-type", "application/json")
                    .addHeader("X-Bmob-Application-Id", X_BMOB_APPLICATION_ID)
                    .addHeader("X-Bmob-REST-API-Key", X_BMOB_REST_API_KEY)
                    .build();
            Response rep = okHttpClient.newCall(req).execute();
            String returnString = rep.body().string();
            System.out.println("返回码：" + rep.code());
            System.out.println("返回内容：" + returnString);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return sr;
    }
}


