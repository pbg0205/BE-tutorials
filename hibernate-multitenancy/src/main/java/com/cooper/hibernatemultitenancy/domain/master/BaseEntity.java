package com.cooper.hibernatemultitenancy.domain.master;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = -2823039950555768617L;

    @CreatedDate
    protected LocalDateTime createdDt;

}
