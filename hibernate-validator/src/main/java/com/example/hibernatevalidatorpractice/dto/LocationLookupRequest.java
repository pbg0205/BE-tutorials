package com.example.hibernatevalidatorpractice.dto;

import com.example.hibernatevalidatorpractice.annotation.Latitude;
import com.example.hibernatevalidatorpractice.annotation.Longitude;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LocationLookupRequest {

    @Longitude
    private final double longitude;

    @Latitude
    private final double latitude;

}
