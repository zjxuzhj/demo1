package com.zhj.demo1.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component // 或@Service
public class TaskService {
    //每天15:12触发事件
    @Scheduled(cron = "0 12 15 ? * *") // 每隔1秒执行一次
    public void timerRate() {
        // 在此处执行调度任务。
        System.out.println("aaaaaaa" + System.currentTimeMillis());
    }
}
