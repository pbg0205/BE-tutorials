package com.cooper.hibernatemultitenancy.domain.master;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "tb_tenant")
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tenant extends BaseEntity {

    private static final long serialVersionUID = -3816119634165804470L;

    @Id
    private String id;

    private String dbName;

    private String dbAddress;

    private String dbUsername;

    private String dbPassword;

    @Builder
    public Tenant(String id, String dbName, String dbAddress, String dbUsername, String dbPassword) {
        this.id = id;
        this.dbName = dbName;
        this.dbAddress = dbAddress;
        this.dbUsername = dbUsername;
        this.dbPassword = dbPassword;
    }

    public String getJdbcUrl() {
        return String.format("jdbc:mysql://%s/%s?createDatabaseIfNotExist=true", dbAddress, dbName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tenant tenant = (Tenant) o;
        return Objects.equals(id, tenant.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
