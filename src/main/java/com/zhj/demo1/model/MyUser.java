package com.zhj.demo1.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by HongJay on 2019-07-19.
 */
public class MyUser {
    private String nickname;
    private String password_t;
    private String username;
    private String objectId;
    private String sessionToken;
    private boolean vip;
    private BmobDate vipdate;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword_t() {
        return password_t;
    }

    public void setPassword_t(String password_t) {
        this.password_t = password_t;
    }

    public boolean isVip() {
        return vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }

    public BmobDate getVipdate() {
        return vipdate;
    }

    public void setVipdate(BmobDate vipdate) {
        this.vipdate = vipdate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public static class BmobDate implements Serializable {
        private static final long serialVersionUID = -7739760111722811743L;
        private String iso;
        private String __type = "Date";

        public BmobDate(Date date) {
            SimpleDateFormat var2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            this.iso = var2.format(date);
        }

        public static long getTimeStamp(String createdAt) {
            long var1 = 0L;

            try {
                var1 = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(createdAt).getTime();
            } catch (Exception var4) {
                var4.printStackTrace();
            }

            return var1;
        }

        public String getDate() {
            return this.iso;
        }
    }
}
