package com.example.hibernatevalidatorpractice.business;

import com.example.hibernatevalidatorpractice.dto.LocationLookupRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LocationServiceTest {

    @Autowired
    private LocationService locationService;

    @DisplayName("로케이션 검증 테스트")
    @Test
    void getLocationValidation() {
        //given
        LocationLookupRequest locationLookupRequest = new LocationLookupRequest(-181, -91);

        locationService.getLocation(locationLookupRequest);
    }

}
