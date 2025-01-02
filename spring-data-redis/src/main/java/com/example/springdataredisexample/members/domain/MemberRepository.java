package com.example.springdataredisexample.members.domain;

import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository <Member, String> {
}
