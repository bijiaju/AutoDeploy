package com.hp.docker_base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by kemp on 2018/8/15.
 */
@Controller
public class MyController {


    @Autowired
    private WebSocket webSocket;

    @RequestMapping("/index")
    public String test(Model model){
        Set<String> clients =new LinkedHashSet<String>();
        ConcurrentHashMap<String, WebSocket> currentCients = webSocket.getCurrentCient();
        for(Map.Entry<String, WebSocket> entry: currentCients.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            clients.add(entry.getKey());
        }
        model.addAttribute("clients",clients);
        return "index";
    }


    @RequestMapping("/p2p")
    public String execSQL(Model model, @RequestParam(defaultValue = "")String name, String message){
        model.addAttribute("name",name);
        model.addAttribute("message",message);
        System.out.println(message);
        return "p2p";
    }

}