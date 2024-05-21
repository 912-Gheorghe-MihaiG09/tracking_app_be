package com.mihaigheorghe.tracking.dto;

import com.mihaigheorghe.tracking.domain.device.LocationData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationDataDTO {
    private Double latitude;

    private Double longitude;

    private Date date;

    private String device;

    public static LocationDataDTO from(LocationData location) {
        return new LocationDataDTO(
                location.getLatitude(),
                location.getLongitude(),
                location.getDate(),
                location.getDevice().getSerialNumber()
        );
    }
}
