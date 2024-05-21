package com.mihaigheorghe.tracking.domain.device;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Table
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationData {

    @Id
    @GeneratedValue
    private Long id;

    private Double latitude;

    private Double longitude;

    private Date date;

    @ManyToOne
    @JoinColumn(name = "device_serial_number", nullable = false)
    private Device device;
}
