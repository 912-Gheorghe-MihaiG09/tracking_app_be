package com.mihaigheorghe.tracking.service;

import com.mihaigheorghe.tracking.domain.device.Device;
import com.mihaigheorghe.tracking.domain.device.LocationData;
import com.mihaigheorghe.tracking.domain.user.User;
import com.mihaigheorghe.tracking.dto.DeviceDTO;
import com.mihaigheorghe.tracking.dto.LocationDataDTO;
import com.mihaigheorghe.tracking.dto.LocationDataRequestDTO;
import com.mihaigheorghe.tracking.repository.DeviceRepository;
import com.mihaigheorghe.tracking.repository.LocationDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin(maxAge = 3600)
@Service
public class DeviceService {
    private final DeviceRepository deviceRepository;
    private final LocationDataRepository locationDataRepository;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository, LocationDataRepository locationDataRepository) {
        this.deviceRepository = deviceRepository;
        this.locationDataRepository = locationDataRepository;
    }

    public String registerDevice(){
        Device device = new Device();
        return deviceRepository.save(device).getSerialNumber();
    }

    public DeviceDTO renameDevice(String serialNumber, String name){
        Optional<Device> device = deviceRepository.findById(serialNumber);
        if(device.isPresent()){
            device.get().setName(name);
            return DeviceDTO.from(deviceRepository.save(device.get()));
        }
        return null;
    }

    public List<DeviceDTO> getDevices(){
        return deviceRepository.findAll().stream().map(DeviceDTO::from).toList();
    }

    public Optional<DeviceDTO> pairDevice(String serialNumber, User user){
        Optional<Device> device = deviceRepository.findById(serialNumber);
        if(device.isPresent()){
            device.get().getPairedUsers().add(user);
            deviceRepository.save(device.get());
            return Optional.of(DeviceDTO.from(device.get()));
        }
        return Optional.empty();
    }

    public List<DeviceDTO> getUserDevices(User user){
        return deviceRepository.findBypairedUsers(user).stream().map(DeviceDTO::from).toList();
    }

    public LocationDataDTO registerLocation(LocationDataRequestDTO requestDTO){
        Optional<Device> device = deviceRepository.findById(requestDTO.getDeviceSerialNumber());
        if(device.isPresent()){
            LocationData locationData = LocationData.builder().
                    latitude(requestDTO.getLatitude()).
                    longitude(requestDTO.getLongitude()).
                    date(new Date()).device(device.get()).build();
            return LocationDataDTO.from(locationDataRepository.save(locationData));
        }
        return null;
    }
}
