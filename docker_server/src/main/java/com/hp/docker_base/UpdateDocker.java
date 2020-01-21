package com.hp.docker_base;

import com.hp.docker_base.enum1.OSinfo;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@Configurable
@EnableScheduling//开启定时任务
public class UpdateDocker {

    @Scheduled(cron = "* * * * * * ")
    public void updateDocker(){
        //System.out.println ("updateDocker...");
       // System.out.println ("发邮件时间： " + dateFormat ().format (new Date ()));
       // sendAttachmentMail();
    }


    public static void main(String [] args){
        System.out.println( System.getProperty("os.name") );
        System.out.println( System.getProperty("os.version") );
        System.out.println( System.getProperty("os.arch") );

        if(OSinfo.isWindows()){
            System.out.println( "当前更新不支持"+ System.getProperty("os.name"));
        }else if(OSinfo.isLinux()){
            System.out.println( "当前更新支持"+ System.getProperty("os.name"));
        }
    }
}
