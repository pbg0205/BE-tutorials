package com.cooper.hibernatemultitenancy.repository.tenant;

import com.cooper.hibernatemultitenancy.domain.tenant.CompanyHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyHistoryRepository extends JpaRepository<CompanyHistory, Long> {
}
