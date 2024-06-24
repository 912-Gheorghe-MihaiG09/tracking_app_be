package com.mihaigheorghe.tracking.dto;

import com.mihaigheorghe.tracking.domain.device.Geofence;
import lombok.Data;

@Data
public class GeofenceDTO {
    private Double latitude;
    private Double longitude;
    private Double radius;


    public static Boolean isValid(GeofenceDTO dto){
        return dto.latitude != null && dto.longitude != null && dto.radius != null;
    };

    static GeofenceDTO from(Geofence geofence){
        GeofenceDTO dto = new GeofenceDTO();
        dto.latitude = geofence.getLatitude();
        dto.longitude = geofence.getLongitude();
        dto.radius = geofence.getRadius();

        return dto;
    }
}
