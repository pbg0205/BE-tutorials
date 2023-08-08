package com.cooper.hibernatemultitenancy.config.async;

import com.cooper.hibernatemultitenancy.tenant.TenantContextHolder;
import org.springframework.core.task.TaskDecorator;

public class TenantAwareTaskDecorator implements TaskDecorator {

    @Override
    public Runnable decorate(Runnable runnable) {
        String tenantId = TenantContextHolder.getTenantId();
        return () -> {
            try {
                TenantContextHolder.setTenantId(tenantId);
                runnable.run();
            } finally {
                TenantContextHolder.clear();
            }
        };
    }

}
