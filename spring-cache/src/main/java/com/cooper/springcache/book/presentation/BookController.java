package com.cooper.springcache.book.presentation;

import com.cooper.springcache.book.business.BookService;
import com.cooper.springcache.book.dto.BookCreateRequestDto;
import com.cooper.springcache.book.dto.BookCreateResponseDto;
import com.cooper.springcache.book.dto.BookLookupResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class BookController {

    private final BookService bookService;
    private final Logger log = LoggerFactory.getLogger(BookController.class);

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/v1/books")
    public ResponseEntity<BookCreateResponseDto> createBook(@RequestBody BookCreateRequestDto bookCreateRequestDto) {
        BookCreateResponseDto bookCreateResponseDto = bookService.createBook(bookCreateRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bookCreateResponseDto);
    }

    @GetMapping("/v1/books/{bookId}")
    public ResponseEntity<BookLookupResponseDto> findBookById(@PathVariable String bookId) {
        long startTime = System.currentTimeMillis();
        log.debug("startTime : {}", startTime);

        if (bookId.equals("1")) {
            bookId = null;
        }
        BookLookupResponseDto bookLookupResponseDto = bookService.findBookById(bookId);

        long endTime = System.currentTimeMillis();
        log.debug("endTime : {}", endTime);

        log.debug("elapsedTIme : {}", endTime - startTime);

        return ResponseEntity.status(HttpStatus.OK)
                .body(bookLookupResponseDto);
    }

    @DeleteMapping("/v1/books/{bookId}")
    public ResponseEntity<Void> deleteBookById(@PathVariable String bookId) {
        long startTime = System.currentTimeMillis();
        log.debug("startTime : {}", startTime);

        bookService.deleteBookById(bookId);

        long endTime = System.currentTimeMillis();
        log.debug("endTime : {}", endTime);

        log.debug("elapsedTIme : {}", endTime - startTime);

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }

}
