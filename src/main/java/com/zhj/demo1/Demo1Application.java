package com.zhj.demo1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Demo1Application {

    public static void main(String[] args) {
        SpringApplication.run(Demo1Application.class, args);
//        LoginController loginController=new LoginController();
//        loginController.doPostGetJson();
//        Timer newWaitTimer = new Timer();
//        TimerTask waitTimerTask = new TimerTask() {
//            @Override
//            public void run() {
//                System.out.println("aaaa");
//            }
//        };
//        newWaitTimer.schedule(waitTimerTask, 0, 2000);

    }
}
