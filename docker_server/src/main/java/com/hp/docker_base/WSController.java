package com.hp.docker_base;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by kemp on 2018/8/15.
 */
@RequestMapping(value = "/ws")
@RestController
public class WSController {

    @Value("${fileDownPath}")
    public String fileDownPath;


    @Autowired
    private WebSocket webSocket;


    @RequestMapping("/getAllClients")
    public LinkedHashMap<String, Object> getAllClients(String cmd, HttpServletResponse httpResponse, HttpServletRequest httpServletRequest){
        httpResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpResponse.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");

        LinkedHashMap<String,Object> reMap = new LinkedHashMap<>();
        Set<String> clients =new LinkedHashSet<String>();
        ConcurrentHashMap<String, WebSocket> currentCients = webSocket.getCurrentCient();
        for(Map.Entry<String, WebSocket> entry: currentCients.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            clients.add(entry.getKey());
            //clients.put("client",entry.getKey());
            //clients.add();
        }
        reMap.put("clients",clients);
        return reMap;
    }

    @RequestMapping("/execCmd")
    public void execSQL(String cmd){
        String cmd1 = cmd.split("###")[0];
        System.out.println("格式化命令："+cmd1);
        switch (cmd1){
            case "execSQL":
                webSocket.GroupSending("execSQL");
                break;
            case "formatCmd":
                webSocket.GroupSending("formatCmd");
                break;
            case "execUpgrade":
                webSocket.GroupSending("execUpgrade");
                break;

            case "restartClient":
                webSocket.GroupSending("restartClient");
                break;

            case "updateDwSource":
                webSocket.GroupSending("updateDwSource");
                break;

            case "dwUpgradeFile":
                webSocket.GroupSending("dwUpgradeFile");
                break;

            case "startApp":
                webSocket.GroupSending("startAllApp");
                break;

            default:
                break;
        }

    }


    /**
     * 从项目文件升级文件
     * @param response
     * @return
     */
    @RequestMapping("/download/{toolName}")
    public String downLoad(@PathVariable("toolName") String filename,HttpServletResponse response) throws Exception{
        //File file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "upgrade/"+filename);
        File file = new File(fileDownPath+"/"+filename);
        if(file.exists()) { //判断文件父目录是否存在
            //下面是通知浏览器以下载的形式，down下来
            try {
                filename = new String(filename.getBytes(), "ISO-8859-1");//为了解决中文下载乱码问题
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment;fileName=" + filename);

            OutputStream os = response.getOutputStream();
            FileInputStream fis = new FileInputStream(file);
            IOUtils.copy(fis, os);

        }
        return null;
    }




    @RequestMapping("/AppointSending")
    public void AppointSending(@RequestParam(defaultValue = "")String name,String message){
        String cmd1 = message.split("###")[0];
        webSocket.AppointSending(name,cmd1);
    }

}