package com.mihaigheorghe.tracking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller("/api/ping")
public class PingController {

//    private final SimpMessagingTemplate simpMessagingTemplate;
//
//    @Autowired
//    public PingController(SimpMessagingTemplate simpMessagingTemplate) {
//        this.simpMessagingTemplate = simpMessagingTemplate;
//    }
//
//    @PostMapping("/{deviceSerialNumber}")
//    public String ping(@PathVariable String deviceSerialNumber) {
//        String message = "ping the following device: " + deviceSerialNumber;
//        simpMessagingTemplate.convertAndSend("/tutorial", message);
//        return message;
//    }
}
