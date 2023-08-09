package com.cooper.hibernatemultitenancy.service;

import com.cooper.hibernatemultitenancy.domain.master.Tenant;
import com.cooper.hibernatemultitenancy.repository.master.TenantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MasterService {

    private final TenantRepository tenantRepository;

    @Transactional
    public void nested(String tenantId) {
        Tenant tenant = tenantRepository.findById(tenantId).orElseThrow(RuntimeException::new);
        log.info("tenant: {}", tenant);
        log.info("master.test");
    }

}
