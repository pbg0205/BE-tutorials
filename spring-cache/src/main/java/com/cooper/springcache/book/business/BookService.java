package com.cooper.springcache.book.business;

import com.cooper.springcache.book.domain.Book;
import com.cooper.springcache.book.domain.BookRepository;
import com.cooper.springcache.book.dto.BookCreateRequestDto;
import com.cooper.springcache.book.dto.BookCreateResponseDto;
import com.cooper.springcache.book.dto.BookLookupResponseDto;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookCreateResponseDto createBook(BookCreateRequestDto bookCreateRequestDto) {
        Book book = Book.create(bookCreateRequestDto.getTitle());
        bookRepository.save(book);
        return new BookCreateResponseDto(book.getId(), book.getTitle());
    }

    @Cacheable(key = "#bookId", value = "book")
    public BookLookupResponseDto findBookById(String bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(RuntimeException::new);
        return new BookLookupResponseDto(book.getId(), book.getTitle());
    }

    @CacheEvict(key = "#bookId", value = "book")
    public void deleteBookById(String bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(RuntimeException::new);
        bookRepository.delete(book);
    }

}