package com.mihaigheorghe.tracking.domain.device;

import com.mihaigheorghe.tracking.domain.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;


@Entity
@Table
@Data
public class Device {
    @Id
    @GeneratedValue(generator = "prod-generator")
    @GenericGenerator(name = "prod-generator",
            strategy = "com.mihaigheorghe.tracking.utils.DeviceSerialNumberGenerator")
    private String serialNumber;

    private String name;

    private String category = "OTHER";

    @OneToOne
    private Geofence geofence;

    private Boolean isLocked = false;

    @OneToMany(mappedBy = "device")
    private List<LocationData> locationHistory;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "user_device",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "device_id"))
    private List<User> pairedUsers;
}
