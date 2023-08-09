package com.cooper.hibernatemultitenancy.listener;

import com.cooper.hibernatemultitenancy.domain.tenant.CompanyHistory;
import com.cooper.hibernatemultitenancy.dto.CompanyLookupRequest;
import com.cooper.hibernatemultitenancy.repository.tenant.CompanyHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CompanyHistoryListener {

    private final CompanyHistoryRepository companyHistoryRepository;

    @Async
    @TransactionalEventListener
    @Transactional(transactionManager = "tenantTransactionManager")
    public void saveCompanyHistory(CompanyLookupRequest companyLookupRequest) {
        List<CompanyHistory> companyHistories = companyLookupRequest.getCompanyIds().stream()
                .map(CompanyHistory::new)
                .collect(Collectors.toList());

        companyHistoryRepository.saveAll(companyHistories);
    }

}
