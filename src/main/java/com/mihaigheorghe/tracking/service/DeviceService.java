package com.mihaigheorghe.tracking.service;

import com.mihaigheorghe.tracking.config.web_socket.MyWebSocketHandler;
import com.mihaigheorghe.tracking.domain.device.Device;
import com.mihaigheorghe.tracking.domain.device.Geofence;
import com.mihaigheorghe.tracking.domain.device.LocationData;
import com.mihaigheorghe.tracking.domain.user.User;
import com.mihaigheorghe.tracking.dto.DeviceDTO;
import com.mihaigheorghe.tracking.dto.GeofenceDTO;
import com.mihaigheorghe.tracking.dto.LocationDataDTO;
import com.mihaigheorghe.tracking.dto.LocationDataRequestDTO;
import com.mihaigheorghe.tracking.repository.DeviceRepository;
import com.mihaigheorghe.tracking.repository.GeofenceRepository;
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
    private final GeofenceRepository geofenceRepository;

    private final MyWebSocketHandler myWebSocketHandler;


    @Autowired
    public DeviceService(DeviceRepository deviceRepository,
                         LocationDataRepository locationDataRepository,
                         GeofenceRepository geofenceRepository,
                         MyWebSocketHandler myWebSocketHandler) {
        this.deviceRepository = deviceRepository;
        this.locationDataRepository = locationDataRepository;
        this.geofenceRepository = geofenceRepository;
        this.myWebSocketHandler = myWebSocketHandler;
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
        Optional<Device> deviceOptional = deviceRepository.findById(serialNumber);
        if(deviceOptional.isPresent()){
            Device device =  deviceOptional.get();
            if(!device.getPairedUsers().contains(user)){
                device.getPairedUsers().add(user);
                deviceRepository.save(device);
            }
            return Optional.of(DeviceDTO.from(device));
        }
        return Optional.empty();
    }

    public void unpairDevice(String serialNumber, User user){
        Optional<Device> deviceOptional = deviceRepository.findById(serialNumber);
        if(deviceOptional.isPresent()){
            Device device =  deviceOptional.get();
            if(device.getPairedUsers().contains(user)){
                device.getPairedUsers().remove(user);
                deviceRepository.save(device);
            }
        }
    }

    public List<DeviceDTO> getUserDevices(User user){
        return deviceRepository.findBypairedUsers(user).stream().map(DeviceDTO::from).toList();
    }

    public DeviceDTO updateDevice(String serialNumber, String name, String category){
        Optional<Device> device = deviceRepository.findById(serialNumber);
        if(device.isPresent()){
            if(name != null && !name.isEmpty()){
                device.get().setName(name);
            }
            if(category != null && !category.isEmpty()){
                device.get().setCategory(category);
            }
            deviceRepository.save(device.get());
            return DeviceDTO.from(device.get());
        }
        return null;
    }

    public LocationDataDTO registerLocation(LocationDataRequestDTO requestDTO){
        Optional<Device> deviceOptional = deviceRepository.findById(requestDTO.getDeviceSerialNumber());
        if(deviceOptional.isPresent()){
            Device device =  deviceOptional.get();
            LocationData locationData = LocationData.builder().
                    latitude(requestDTO.getLatitude()).
                    longitude(requestDTO.getLongitude()).
                    date(new Date()).device(device).build();

            Geofence geofence = device.getGeofence();
            if(geofence != null && device.getIsLocked()){
                if(!GeoUtils.isPointInCircle(
                        locationData.getLatitude(),
                        locationData.getLongitude(),
                        geofence.getLatitude(),
                        geofence.getLongitude(),
                        geofence.getRadius()
                )){
                    myWebSocketHandler.broadcastMessage("notification: " + device.getSerialNumber());
                };
            }


            return LocationDataDTO.from(locationDataRepository.save(locationData));
        }
        return null;
    }


    public DeviceDTO lockDevice(String serialNumber, GeofenceDTO geofenceDTO){
        Optional<Device> deviceOptional = deviceRepository.findById(serialNumber);
        if(deviceOptional.isPresent()){
            Device device =  deviceOptional.get();
            Geofence geofence = new Geofence();
            geofence.setLongitude(geofenceDTO.getLongitude());
            geofence.setLatitude(geofenceDTO.getLatitude());
            geofence.setRadius(geofenceDTO.getRadius());

            Geofence oldGeofence = device.getGeofence();

            geofence = geofenceRepository.save(geofence);
            device.setGeofence(geofence);
            device.setIsLocked(true);


            device = deviceRepository.save(device);

            if(oldGeofence != null){
                geofenceRepository.delete(oldGeofence);
            }

            return DeviceDTO.from(device);
        }
        return null;
    }

    public DeviceDTO unlockDevice(String serialNumber){
        Optional<Device> deviceOptional = deviceRepository.findById(serialNumber);
        if(deviceOptional.isPresent()){
            Device device =  deviceOptional.get();
            device.setIsLocked(false);
            Device result = deviceRepository.save(device);
            return DeviceDTO.from(result);
        }
        return null;
    }

    public void pingDevice(String serialNumber){
        myWebSocketHandler.broadcastMessage("ping: " + serialNumber);
    }
}
