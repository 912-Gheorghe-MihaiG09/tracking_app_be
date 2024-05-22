package com.mihaigheorghe.tracking.controller;

import org.springframework.stereotype.Controller;

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
