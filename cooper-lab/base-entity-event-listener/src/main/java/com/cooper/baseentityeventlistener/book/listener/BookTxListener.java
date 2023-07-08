package com.cooper.baseentityeventlistener.book.listener;

import com.cooper.baseentityeventlistener.book.domain.Book;
import com.cooper.baseentityeventlistener.book.domain.BookHistory;
import com.cooper.baseentityeventlistener.book.domain.BookHistoryRepository;
import com.cooper.baseentityeventlistener.book.domain.BookTxHistoryType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class BookTxListener {

    private final BookHistoryRepository bookHistoryRepository;

    @Async
    @TransactionalEventListener
    @Transactional
    public void commitSuccessLog(Book book) {
        BookHistory bookHistory = new BookHistory(BookTxHistoryType.COMMIT, book.getIsbn());
        bookHistoryRepository.save(bookHistory);
        log.info("[[Current Thread: {}]][commitSuccessLog] - book history: {}", Thread.currentThread(), bookHistory);
    }

}
