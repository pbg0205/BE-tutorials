package com.cooper.hibernatemultitenancy.repository.tenant;

import com.cooper.hibernatemultitenancy.domain.tenant.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, String> {
}
