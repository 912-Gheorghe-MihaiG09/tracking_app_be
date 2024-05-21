package com.mihaigheorghe.tracking.controller;

import com.mihaigheorghe.tracking.domain.device.Device;
import com.mihaigheorghe.tracking.domain.user.User;
import com.mihaigheorghe.tracking.dto.DeviceDTO;
import com.mihaigheorghe.tracking.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user/device")
public class DeviceUserController {
    private final DeviceService deviceService;

    @Autowired
    public DeviceUserController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping("")
    public ResponseEntity<List<DeviceDTO>> getDevices() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        deviceService.getUserDevices(user);
        return ResponseEntity.ok(deviceService.getUserDevices(user));
    }

    @PostMapping("/pair/{serial_number}")
    public ResponseEntity<DeviceDTO> pairDevice(
            @PathVariable String serial_number
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        Optional<DeviceDTO> result = deviceService.pairDevice(serial_number, user);
        if (result.isPresent()) {
            return ResponseEntity.ok(result.get());
        }
        return ResponseEntity.notFound().build();
    }
}
