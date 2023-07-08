package com.cooper.baseentityeventlistener.book.listener;

import com.cooper.baseentityeventlistener.book.application.BookService;
import com.cooper.baseentityeventlistener.book.domain.Book;
import com.cooper.baseentityeventlistener.book.domain.BookHistory;
import com.cooper.baseentityeventlistener.book.domain.BookHistoryRepository;
import com.cooper.baseentityeventlistener.book.domain.BookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BookTxListenerTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookHistoryRepository bookHistoryRepository;

    @Test
    @DisplayName("북 생성에 대한 트랜잭션 확인")
    void createCommitHistory() {
        //given, when
        bookService.createBook("자바의 정석", "1111111");

        //then
        Book book = bookRepository.findById(1L).get();
        BookHistory bookHistory = bookHistoryRepository.findById(1L).get();

        System.out.println(book);
        System.out.println(bookHistory);

        assertThat(bookHistoryRepository.findAll()).hasSize(1);
    }

}
