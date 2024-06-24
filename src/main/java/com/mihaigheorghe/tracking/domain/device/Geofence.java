package com.mihaigheorghe.tracking.domain.device;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table
@Entity
public class Geofence{
    @Id
    @GeneratedValue
    private Long id;

    double latitude;
    double longitude;
    double radius;
}
