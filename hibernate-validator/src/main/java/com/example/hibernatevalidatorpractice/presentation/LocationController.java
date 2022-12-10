package com.example.hibernatevalidatorpractice.presentation;

import com.example.hibernatevalidatorpractice.annotation.Latitude;
import com.example.hibernatevalidatorpractice.annotation.Longitude;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/location")
@RequiredArgsConstructor
@Validated
public class LocationController {

    @GetMapping
    public ResponseEntity<String> findLocation(
            @RequestParam @Latitude double latitude,
            @RequestParam @Longitude double longitude
    ) {
        System.out.println("latitude = " + latitude);
        System.out.println("longitude = " + longitude);

        return ResponseEntity.ok("ok");
    }

}
