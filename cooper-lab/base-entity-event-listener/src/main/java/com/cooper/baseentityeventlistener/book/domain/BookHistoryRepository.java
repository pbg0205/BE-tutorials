package com.cooper.baseentityeventlistener.book.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookHistoryRepository extends JpaRepository<BookHistory, Long> {
}
