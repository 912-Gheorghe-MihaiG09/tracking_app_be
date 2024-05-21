package com.mihaigheorghe.tracking.dto;

import lombok.Data;

@Data
public class LocationDataRequestDTO {
    private String deviceSerialNumber;
    private double latitude;
    private double longitude;
}
