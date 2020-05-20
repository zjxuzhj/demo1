package com.zhj.demo1.contorller;

import com.google.gson.Gson;
import com.zhj.demo1.api.BaseInterceptor;
import com.zhj.demo1.model.MyUser;
import com.zhj.demo1.model.OrderMore;
import com.zhj.demo1.model.OrderResult;
import com.zhj.demo1.model.UserResult;
import com.zhj.demo1.utils.TimeUtils;
import okhttp3.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * 会员相关
 */
@RestController
public class VipController {
    public final static String LoveStagesTemp_URL = "https://api2.bmob.cn/1/classes/LoveStagesTemp";
    public final static String LoveStages_URL = "https://api2.bmob.cn/1/classes/LoveStages";
    public final static String User_URL = "https://api2.bmob.cn/1/users";
    public final static String Order_URL = "https://api2.bmob.cn/1/classes/Order";
    public final static String Login_URL = "https://api2.bmob.cn/1/login";
    private OkHttpClient okHttpClient;

    /**
     * 用于接收用户充值会员信息
     */
    @RequestMapping(value = "/becomeVip", method = RequestMethod.POST)
    public void becomeVip(@RequestParam("more") String more, @RequestParam("order_id") String order_id) {
        //more解码
        try {
            more = URLDecoder.decode(more, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("more:" + more + " order_id:" + order_id);
        OrderMore orderMore = new Gson().fromJson(more, OrderMore.class);

        // 要调用的接口方法
        okHttpClient = new OkHttpClient.Builder().addInterceptor(new BaseInterceptor()).build();
        try {
            //获得用户
            HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(User_URL))
                    .newBuilder();
            urlBuilder.addQueryParameter("where", "{\"objectId\":" + "\"" + orderMore.getObjectId() + "\"" + "}");
            Request req = new Request.Builder()
                    .url(urlBuilder.build())
                    .build();
            Response rep = okHttpClient.newCall(req).execute();
            String returnString = rep.body().string();
            System.out.println("查询充值用户 返回码：" + rep.code() + " 返回内容：" + returnString);
            UserResult userResult = new Gson().fromJson(returnString, UserResult.class);
            if (userResult == null || userResult.getResults() == null || userResult.getResults().get(0) == null) {
                System.out.println("查询充值用户 数量为0");
                return;
            }

            //获得用户登录token
            urlBuilder = Objects.requireNonNull(HttpUrl.parse(Login_URL))
                    .newBuilder();
            urlBuilder.addQueryParameter("username", userResult.getResults().get(0).getUsername())
                    .addQueryParameter("password", userResult.getResults().get(0).getPassword_t());
            req = new Request.Builder().url(urlBuilder.build()).build();
            rep = okHttpClient.newCall(req).execute();
            returnString = rep.body().string();
            System.out.println("充值登录用户 返回码：" + rep.code() + " 返回内容：" + returnString);
            MyUser myUser = new Gson().fromJson(returnString, MyUser.class);

            //修改订单状态
            urlBuilder = Objects.requireNonNull(HttpUrl.parse(Order_URL))
                    .newBuilder();
            urlBuilder.addQueryParameter("where", "{\"orderId\":" + "\"" + order_id + "\"}");
            req = new Request.Builder()
                    .url(urlBuilder.build())
                    .build();
            rep = okHttpClient.newCall(req).execute();
            returnString = rep.body().string();
            System.out.println("查询充值用户 返回码：" + rep.code() + " 返回内容：" + returnString);
            OrderResult orderResult = new Gson().fromJson(returnString, OrderResult.class);

            if (orderResult != null && orderResult.getResults() != null && orderResult.getResults().get(0) != null) {
                urlBuilder = Objects.requireNonNull(HttpUrl.parse(Order_URL + "/" + orderResult.getResults().get(0).getObjectId()))
                        .newBuilder();
                RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"),
                        "{\"orderStatus\":1}");
                req = new Request.Builder()
                        .url(urlBuilder.build())
                        .put(requestBody)
                        .build();
                rep = okHttpClient.newCall(req).execute();
                returnString = rep.body().string();
                if (rep.code() == 200) {
                    System.out.println("订单状态修改成功");
                } else {
                    System.out.println("订单状态修改失败 返回内容：" + returnString);
                }
            } else {
                System.out.println("未查询到订单 修改订单状态失败");
            }

            //修改用户付费状态和会员时间
            urlBuilder = Objects.requireNonNull(HttpUrl.parse(User_URL + "/" + myUser.getObjectId()))
                    .newBuilder();
            String date2String = TimeUtils.date2String(TimeUtils.subMonthToDate(new Date(), orderMore.getMonthNum()));
            RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"),
                    "{\"vip\":true,\"vipdate\":{\"__type\":\"Date\",\"iso\":\"" + date2String + "\"}}");
            req = new Request.Builder()
                    .url(urlBuilder.build())
                    .addHeader("X-Bmob-Session-Token", myUser.getSessionToken())
                    .put(requestBody)
                    .build();
            rep = okHttpClient.newCall(req).execute();
            returnString = rep.body().string();
            if (rep.code() == 200) {
                System.out.println(myUser.getUsername() + " " + myUser.getNickname() + " 会员充值成功");
            } else {
                System.out.println("修改失败 返回内容：" + returnString);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * 用于接口调用或循环任务自动执行每天检查过期用户
     *
     * @return 传入的参数暂时无用，反参也无用
     */
    @RequestMapping("/updateVip")
    public void updateVip() {
        // 要调用的接口方法
        okHttpClient = new OkHttpClient.Builder().addInterceptor(new BaseInterceptor()).build();
        try {
            HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(User_URL))
                    .newBuilder();
            urlBuilder.addQueryParameter("where", "{\"vip\":true}");
            Request req = new Request.Builder()
                    .url(urlBuilder.build())
                    .build();
            Response rep = okHttpClient.newCall(req).execute();
            String returnString = rep.body().string();
            System.out.println("查询当前会员 返回码：" + rep.code());
            System.out.println("查询当前会员 返回内容：" + returnString);
            UserResult userResult = new Gson().fromJson(returnString, UserResult.class);
            for (MyUser result : userResult.getResults()) {
                SimpleDateFormat var2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date vipDate = var2.parse(result.getVipdate().getDate());
                if (vipDate.before(new Date())) {
                    System.out.println(result.getNickname() + " 过期了");
                    urlBuilder = Objects.requireNonNull(HttpUrl.parse(Login_URL))
                            .newBuilder();
                    urlBuilder.addQueryParameter("username", result.getUsername())
                            .addQueryParameter("password", result.getPassword_t());
                    req = new Request.Builder().url(urlBuilder.build()).build();
                    rep = okHttpClient.newCall(req).execute();
                    returnString = rep.body().string();
                    System.out.println("登录用户 返回码：" + rep.code());
                    System.out.println("登录用户 返回内容：" + returnString);


                    MyUser myUser = new Gson().fromJson(returnString, MyUser.class);
                    urlBuilder = Objects.requireNonNull(HttpUrl.parse(User_URL + "/" + myUser.getObjectId()))
                            .newBuilder();
                    RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), "{\"vip\":false}");
                    req = new Request.Builder()
                            .url(urlBuilder.build())
                            .addHeader("X-Bmob-Session-Token", myUser.getSessionToken())
                            .put(requestBody)
                            .build();
                    rep = okHttpClient.newCall(req).execute();
                    returnString = rep.body().string();
                    if (rep.code() == 200) {
                        System.out.println(myUser.getUsername() + " " + myUser.getNickname() + " 会员状态修改成功");
                    } else {
                        System.out.println("修改失败 返回内容：" + returnString);
                    }
                } else {
//                    System.out.println(result.getNickname() + " 没过期");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}


