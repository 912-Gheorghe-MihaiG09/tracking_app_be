package com.mihaigheorghe.tracking.dto;

import com.mihaigheorghe.tracking.domain.device.Device;
import com.mihaigheorghe.tracking.domain.device.LocationData;
import com.mihaigheorghe.tracking.domain.user.User;
import lombok.Data;

import java.util.List;

@Data
public class DeviceDTO {
    private String serialNumber;

    private String name;

    private List<LocationDataDTO> locationHistory;

    private LocationDataDTO location;

    private List<String> pairedUsers;

    private String category;

    private GeofenceDTO geofence;

    private Boolean isLocked;

    public static DeviceDTO from(Device device) {
        DeviceDTO deviceDTO = new DeviceDTO();
        List<LocationData> locationHistory = device.getLocationHistory();
        deviceDTO.setSerialNumber(device.getSerialNumber());
        deviceDTO.setName(device.getName());
        if(!locationHistory.isEmpty()) {
            deviceDTO.setLocation(LocationDataDTO.from(device.getLocationHistory().getLast()));
        }
        deviceDTO.setCategory(device.getCategory());
        deviceDTO.setLocationHistory(device.getLocationHistory().stream().map(LocationDataDTO::from).toList());
        deviceDTO.setPairedUsers(
                device.getPairedUsers().stream().map(User::getUsername).toList()
        );
        if(device.getGeofence() != null) {
            deviceDTO.setGeofence(GeofenceDTO.from(device.getGeofence()));
        }
        deviceDTO.setIsLocked(device.getIsLocked());
        return deviceDTO;
    }
}
