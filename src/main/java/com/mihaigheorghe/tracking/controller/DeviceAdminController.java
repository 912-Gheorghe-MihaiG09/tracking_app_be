package com.mihaigheorghe.tracking.controller;

import com.mihaigheorghe.tracking.domain.device.Device;
import com.mihaigheorghe.tracking.dto.DeviceDTO;
import com.mihaigheorghe.tracking.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/device")
public class DeviceAdminController {
    private final DeviceService deviceService;

    @Autowired
    public DeviceAdminController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping("/register")
    public String registerDevice() {
        return deviceService.registerDevice();
    }

    @PostMapping("/rename")
    public ResponseEntity<DeviceDTO> renameDevice(@RequestParam String serialNumber, @RequestParam String newName) {
        DeviceDTO result =  deviceService.renameDevice(serialNumber, newName);
        if(result == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("")
    public ResponseEntity<List<DeviceDTO>> getAllDevices() {
        List<DeviceDTO> result =  deviceService.getDevices();
        return ResponseEntity.ok(result);
    }
}
