package com.zhj.demo1.service;

import com.zhj.demo1.contorller.LoginController;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component // 或@Service
public class TaskService {
    //每天18:00触发事件
//    @Scheduled(cron = "0 00 18 ? * *") // 每隔1秒执行一次
    @Scheduled(cron = "0/20 * * * * ?")  //每隔5秒执行一次
    public void timerRate() {
        // 在此处执行调度任务。
        System.out.println("aaaaaaa" + System.currentTimeMillis());
        LoginController loginController = new LoginController();
        loginController.getLoveStagesTemp();
    }
}
