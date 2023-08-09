package com.cooper.hibernatemultitenancy.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class CompanyLookupRequest {

    private final List<String> companyIds;

}
