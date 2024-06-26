package com.mihaigheorghe.tracking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationDataRequestDTO {
    private String deviceSerialNumber;
    private double latitude;
    private double longitude;
}
