package com.mihaigheorghe.tracking.repository;

import com.mihaigheorghe.tracking.domain.device.Device;
import com.mihaigheorghe.tracking.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, String> {
    List<Device> findBypairedUsers(User user);
}
