package com.cooper.baseentityeventlistener.book.application;

import com.cooper.baseentityeventlistener.book.domain.Book;
import com.cooper.baseentityeventlistener.book.domain.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public Book createBook(String bookName, String isbn) {
        log.info("[createBook] bookName: {}, isbn: {}", bookName, isbn);
        Book book = bookRepository.save(new Book(bookName, isbn));
        log.info("[[Current Thread: {}]][createBook] - book: {}", Thread.currentThread(), book);
        applicationEventPublisher.publishEvent(book);
        return book;
    }

}
