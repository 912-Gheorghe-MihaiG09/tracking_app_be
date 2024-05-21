package com.mihaigheorghe.tracking.dto;

import com.mihaigheorghe.tracking.domain.device.Device;
import com.mihaigheorghe.tracking.domain.user.User;
import lombok.Data;

import java.util.List;

@Data
public class DeviceDTO {
    private String serialNumber;

    private String name;

    private List<LocationDataDTO> locationHistory;

    private List<String> pairedUsers;

    public static DeviceDTO from(Device device) {
        DeviceDTO deviceDTO = new DeviceDTO();
        deviceDTO.setSerialNumber(device.getSerialNumber());
        deviceDTO.setName(device.getName());
        deviceDTO.setLocationHistory(device.getLocationHistory().stream().map(LocationDataDTO::from).toList());
        deviceDTO.setPairedUsers(
                device.getPairedUsers().stream().map(User::getUsername).toList()
        );
        return deviceDTO;
    }
}
