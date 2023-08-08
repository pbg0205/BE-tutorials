package com.cooper.hibernatemultitenancy.repository.master;

import com.cooper.hibernatemultitenancy.domain.master.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRepository extends JpaRepository<Tenant, String> {
}
