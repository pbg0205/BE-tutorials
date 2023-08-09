package com.cooper.hibernatemultitenancy.service;

import com.cooper.hibernatemultitenancy.domain.tenant.Company;
import com.cooper.hibernatemultitenancy.dto.CompanyLookupRequest;
import com.cooper.hibernatemultitenancy.repository.tenant.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyService {

    private final MasterService masterService;
    private final CompanyRepository companyRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional(transactionManager = "tenantTransactionManager")
    public List<Company> findAll() {
        List<Company> companies = companyRepository.findAll();
        log.info("findAllAsync: {}", companies);
        return companies;
    }

    @Transactional(transactionManager = "tenantTransactionManager")
    public List<Company> findAllAsync() {
        List<Company> companies = companyRepository.findAll();
        sendCompanyHistory(companies);
        log.info("findAllAsync: {}", companies);
        return companies;
    }

    private void sendCompanyHistory(List<Company> companies) {
        CompanyLookupRequest companyLookupRequest = new CompanyLookupRequest(
                companies.stream().map(company -> company.getId()).collect(Collectors.toList())
        );

        applicationEventPublisher.publishEvent(companyLookupRequest);
    }

    @Transactional(transactionManager = "tenantTransactionManager")
    public void nested(String tenantId) {
        List<Company> companies = companyRepository.findAll();
        log.info("findAllAsync: {}", companies);

        masterService.nested(tenantId);
    }

}
