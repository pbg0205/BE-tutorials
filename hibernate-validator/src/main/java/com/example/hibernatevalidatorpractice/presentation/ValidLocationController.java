package com.example.hibernatevalidatorpractice.presentation;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hibernatevalidatorpractice.annotation.Latitude;
import com.example.hibernatevalidatorpractice.annotation.Longitude;
import com.example.hibernatevalidatorpractice.business.LocationService;
import com.example.hibernatevalidatorpractice.dto.LocationLookupRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/location")
@RequiredArgsConstructor
public class ValidLocationController {

    private final LocationService locationService;

    @GetMapping("/controller/valid")
    public ResponseEntity<String> findLocationController(
            @RequestParam @Valid @Latitude double latitude, // double, Double 에서 미동작
            @RequestParam @Valid @Longitude double longitude // double, Double 에서 미동작
    ) {
        System.out.println("latitude = " + latitude);
        System.out.println("longitude = " + longitude);

        return ResponseEntity.ok("ok");
    }

    @GetMapping("/service/valid")
    public ResponseEntity<String> findLocationService(
            @RequestParam @Latitude double latitude,
            @RequestParam @Longitude double longitude
    ) {
        locationService.getLocation(new LocationLookupRequest(longitude, latitude));
        System.out.println("latitude = " + latitude);
        System.out.println("longitude = " + longitude);

        return ResponseEntity.ok("ok");
    }

}
