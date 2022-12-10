package com.example.hibernatevalidatorpractice.business;

import com.example.hibernatevalidatorpractice.dto.LocationLookupRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Validated
@Service
public class LocationService {

    public void getLocation(@Valid LocationLookupRequest locationLookupRequest) {
        System.out.println("latitude = " + locationLookupRequest.getLatitude());
        System.out.println("longitude = " + locationLookupRequest.getLongitude());
    }

}
