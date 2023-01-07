package com.example.springcore.i18n.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequestMapping("/api/v1/locale")
@RequiredArgsConstructor
public class LocaleController {

    @GetMapping
    public ResponseEntity<String> getNowLocale(Locale locale) {
        return ResponseEntity.ok(locale.getLanguage());
    }

}
