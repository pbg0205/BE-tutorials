package com.cooper.springdatajpatransactional.account.repository;

import com.cooper.springdatajpatransactional.account.domain.AccountHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountHistoryRepository extends JpaRepository<AccountHistory, Long> {
}
