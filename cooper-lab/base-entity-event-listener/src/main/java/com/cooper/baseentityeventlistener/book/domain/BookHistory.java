package com.cooper.baseentityeventlistener.book.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@EntityListeners(AuditingEntityListener.class)
public class BookHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private BookTxHistoryType bookTxHistoryType;

    private String isbn;

    @CreatedDate
    private LocalDateTime createdAt;

    public BookHistory(BookTxHistoryType bookTxHistoryType, String isbn) {
        this.bookTxHistoryType = bookTxHistoryType;
        this.isbn = isbn;
    }

}
