package com.example.springdataredisexample.domain;

import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository <Member, String> {
}
