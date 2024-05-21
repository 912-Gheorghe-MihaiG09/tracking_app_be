package com.mihaigheorghe.tracking.controller;

import com.mihaigheorghe.tracking.domain.device.LocationData;
import com.mihaigheorghe.tracking.dto.LocationDataDTO;
import com.mihaigheorghe.tracking.dto.LocationDataRequestDTO;
import com.mihaigheorghe.tracking.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/iot")
public class DeviceIOTController {
    private final DeviceService deviceService;

    @Autowired
    public DeviceIOTController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping()
    public ResponseEntity<LocationDataDTO> create(@RequestBody LocationDataRequestDTO requestDTO) {
        LocationDataDTO result = deviceService.registerLocation(requestDTO);
        if(result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }
}
