package com.mihaigheorghe.tracking.controller;

import com.mihaigheorghe.tracking.domain.user.User;
import com.mihaigheorghe.tracking.dto.DeviceDTO;
import com.mihaigheorghe.tracking.dto.GeofenceDTO;
import com.mihaigheorghe.tracking.dto.UpdateDeviceDTO;
import com.mihaigheorghe.tracking.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/ping/{serial_number}")
    public void sendMessage(@PathVariable String serial_number) {
        deviceService.pingDevice(serial_number);
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
        System.out.println("principal: " + authentication.getPrincipal().toString());
        User user = (User) authentication.getPrincipal();
        Optional<DeviceDTO> result = deviceService.pairDevice(serial_number, user);
        if (result.isPresent()) {
            return ResponseEntity.ok(result.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/update/{serial_number}")
    public ResponseEntity<DeviceDTO> updateDevice(
            @PathVariable String serial_number,
            @RequestBody UpdateDeviceDTO updateBody
    ){
        DeviceDTO result = deviceService.updateDevice(serial_number, updateBody.getName(), updateBody.getCategory());
        if(result != null) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/unpair/{serial_number}")
    public void unpairDevice(
            @PathVariable String serial_number
    ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        deviceService.unpairDevice(serial_number, user);
    }

    @PostMapping("/lock/{serial_number}")
    public ResponseEntity<DeviceDTO>  lockDevice(
            @PathVariable String serial_number,
            @RequestBody GeofenceDTO geofence
    ){
        if(geofence != null && GeofenceDTO.isValid(geofence)) {
            DeviceDTO result = deviceService.lockDevice(serial_number, geofence);
            if(result == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/unlock/{serial_number}")
    public ResponseEntity<DeviceDTO>  unLockDevice(
            @PathVariable String serial_number
    ){
        DeviceDTO result = deviceService.unlockDevice(serial_number);
        if(result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }
}
