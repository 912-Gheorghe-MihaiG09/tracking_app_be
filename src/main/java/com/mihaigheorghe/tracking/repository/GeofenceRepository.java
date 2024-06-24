package com.mihaigheorghe.tracking.repository;


import com.mihaigheorghe.tracking.domain.device.Geofence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeofenceRepository extends JpaRepository<Geofence, Long> {
}
