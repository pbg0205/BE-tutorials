package com.cooper.hibernatemultitenancy.controller;

import com.cooper.hibernatemultitenancy.domain.master.Tenant;
import com.cooper.hibernatemultitenancy.domain.tenant.Company;
import com.cooper.hibernatemultitenancy.repository.master.TenantRepository;
import com.cooper.hibernatemultitenancy.repository.tenant.CompanyRepository;
import com.cooper.hibernatemultitenancy.service.CompanyService;
import com.cooper.hibernatemultitenancy.tenant.TenantDatabaseHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TenantController {

    private final TenantRepository tenantRepository;
    private final CompanyService companyService;
    private final CompanyRepository companyRepository;

    private final TenantDatabaseHelper tenantDatabaseHelper;

    @PostMapping("/tenant/create-db")
    public String createTenantDatabase(@RequestBody String tenantId) {
        Tenant tenant = createTenant(tenantId);
        tenantDatabaseHelper.executeSchemaExport(tenant);
        return tenant.getDbName();
    }

    private Tenant createTenant(String tenantId) {
        Tenant tenant = Tenant.builder()
                .id(tenantId)
                .dbName("db_" + tenantId)
                .dbAddress("localhost")
                .dbUsername("root")
                .dbPassword("111111")
                .build();

        return tenantRepository.save(tenant);
    }

    @GetMapping("/companies")
    public ResponseEntity<List<Company>> findAllCompanies() {
        List<Company> companies = companyService.findAll();
        return ResponseEntity.ok(companies);
    }

    @PostMapping("/companies")
    public String addCompany() {
        Company company = Company.create("sample-" + System.currentTimeMillis());
        return companyRepository.save(company).getId();
    }

    @GetMapping("/tenant-master-nested")
    public String tenantMaterNested() {
        companyService.nested();
        return "Ok";
    }

}
