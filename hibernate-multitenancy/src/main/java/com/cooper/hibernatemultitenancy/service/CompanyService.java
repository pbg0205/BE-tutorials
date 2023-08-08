package com.cooper.hibernatemultitenancy.service;

import com.cooper.hibernatemultitenancy.domain.tenant.Company;
import com.cooper.hibernatemultitenancy.repository.tenant.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyService {

    private final MasterService masterService;
    private final CompanyRepository companyRepository;

    @Async
    @Transactional(transactionManager = "tenantTransactionManager")
    public List<Company> findAll() {
        List<Company> companies = companyRepository.findAll();
        log.info("findAllAsync: {}", companies);
        return companies;
    }

    @Transactional(transactionManager = "tenantTransactionManager")
    public void nested() {
        masterService.nested();
    }

}
