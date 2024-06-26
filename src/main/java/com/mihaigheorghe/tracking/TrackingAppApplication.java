package com.mihaigheorghe.tracking;

import com.mihaigheorghe.tracking.controller.auth.AuthenticationService;
import com.mihaigheorghe.tracking.controller.auth.RegisterRequest;
import com.mihaigheorghe.tracking.dto.LocationDataRequestDTO;
import com.mihaigheorghe.tracking.service.DeviceService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.mihaigheorghe.tracking.domain.user.Role.ADMIN;
import static com.mihaigheorghe.tracking.domain.user.Role.USER;

@SpringBootApplication
public class TrackingAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrackingAppApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(
            AuthenticationService service,
            DeviceService deviceService
    ) {
        return args -> {
            var admin = RegisterRequest.builder()
                    .firstname("Admin")
                    .lastname("Admin")
                    .email("admin@mail.com")
                    .password("Parola@123")
                    .role(ADMIN)
                    .build();
            System.out.println("Admin token: " + service.register(admin).getAccessToken());

            var user = RegisterRequest.builder()
                    .firstname("Admin")
                    .lastname("Admin")
                    .email("manager@mail.com")
                    .password("Parola@123")
                    .role(USER)
                    .build();
            String firstDeviceSN = deviceService.registerDevice();
            String secondDeviceSN = deviceService.registerDevice();
            String thirdDeviceSN = deviceService.registerDevice();

            System.out.println("User token: " + service.register(user).getAccessToken());
            System.out.println("Device registered: " + firstDeviceSN);
            System.out.println("Device registered: " + secondDeviceSN);
            System.out.println("Device registered: " + thirdDeviceSN);

            LocationDataRequestDTO firstDeviceLocation = new LocationDataRequestDTO(
                    firstDeviceSN,
                    46.7749,
                    23.5863
            );

            LocationDataRequestDTO secondDeviceLocation = new LocationDataRequestDTO(
                    secondDeviceSN,
                    46.7699,
                    23.5888
            );

            LocationDataRequestDTO thirdDeviceLocation = new LocationDataRequestDTO(
                    thirdDeviceSN,
                    46.7792,
                    23.5837
            );

            deviceService.registerLocation(firstDeviceLocation);
            deviceService.registerLocation(secondDeviceLocation);
            deviceService.registerLocation(thirdDeviceLocation);
        };
    }
}
