package com.zhj.demo1.service;

import com.zhj.demo1.contorller.LoginController;
import com.zhj.demo1.contorller.LoveHealingController;
import com.zhj.demo1.contorller.VipController;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component // 或@Service
public class TaskService {
    //每天18:00触发事件
    @Scheduled(cron = "0 21 11 ? * *") // 每隔1秒执行一次
//    @Scheduled(cron = "0/20 * * * * ?")  //每隔5秒执行一次
    public void timerRate() {
        // 在此处执行调度任务。
        System.out.println("执行第一次：：：" + System.currentTimeMillis());
        LoginController loginController = new LoginController();
        loginController.getLoveStagesTemp();

        System.out.println("执行第二次：：：" + System.currentTimeMillis());
        loginController.getLoveStagesTemp();

        //执行vip状态清除任务
        VipController vipController = new VipController();
        vipController.updateVip();

        //每日更新两条实战情话
        LoveHealingController loveHealingController = new LoveHealingController();
        loveHealingController.addLoveHealing();
    }

    @Scheduled(cron = "0 0 * * * ?")  //每隔一小时执行一次
    public void timerStart() {
        // 在此处执行调度任务。
//        System.out.println("每小时打点计时：：：" + System.currentTimeMillis());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        System.out.println("每小时打点计时：：：" + df.format(new Date()));// new Date()为获取当前系统时间
    }
}
