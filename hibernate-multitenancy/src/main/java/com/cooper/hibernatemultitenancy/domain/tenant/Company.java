package com.cooper.hibernatemultitenancy.domain.tenant;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_company")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Company {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "company_id")
    private String id;

    private String name;

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY)
    private List<User> users = new ArrayList<>();

    public static Company create(String name) {
        Company company = new Company();
        company.name = name;
        return company;
    }

}
