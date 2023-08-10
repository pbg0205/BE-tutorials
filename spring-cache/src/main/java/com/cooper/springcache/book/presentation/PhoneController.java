package com.cooper.springcache.book.presentation;

import com.cooper.springcache.book.business.PhoneService;
import com.cooper.springcache.book.dto.PhoneCreateRequestDto;
import com.cooper.springcache.book.dto.PhoneCreateResponseDto;
import com.cooper.springcache.book.dto.PhoneLookupResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api")
public class PhoneController {

    private final PhoneService phoneService;

    public PhoneController(PhoneService phoneService) {
        this.phoneService = phoneService;
    }

    @PostMapping("/v1/phone")
    public ResponseEntity<PhoneCreateResponseDto> createBook(@RequestBody PhoneCreateRequestDto phoneCreateRequestDto) {
        PhoneCreateResponseDto phoneCreateResponseDto = phoneService.createBook(phoneCreateRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(phoneCreateResponseDto);
    }

    @GetMapping("/v1/phone/{phoneId}")
    public ResponseEntity<PhoneLookupResponseDto> findBookById(@PathVariable String phoneId) {
        long startTime = System.currentTimeMillis();
        log.debug("startTime : {}", startTime);

        if (phoneId.equals("1")) {
            phoneId = null;
        }
        PhoneLookupResponseDto phoneLookupResponseDto = phoneService.findBookById(phoneId);

        long endTime = System.currentTimeMillis();
        log.debug("endTime : {}", endTime);

        log.debug("elapsedTIme : {}", endTime - startTime);

        return ResponseEntity.status(HttpStatus.OK)
                .body(phoneLookupResponseDto);
    }

    @DeleteMapping("/v1/phone/{phoneId}")
    public ResponseEntity<Void> deleteBookById(@PathVariable String phoneId) {
        long startTime = System.currentTimeMillis();
        log.debug("startTime : {}", startTime);

        phoneService.deleteBookById(phoneId);

        long endTime = System.currentTimeMillis();
        log.debug("endTime : {}", endTime);

        log.debug("elapsedTIme : {}", endTime - startTime);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

}
