package com.cooper.springdatajpatransactional.account.repository;

import com.cooper.springdatajpatransactional.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
