package com.mihaigheorghe.tracking.repository;

import com.mihaigheorghe.tracking.domain.device.LocationData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationDataRepository extends JpaRepository<LocationData, Long> {
}
