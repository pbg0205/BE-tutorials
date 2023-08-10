package com.cooper.springcache.book.business;

import com.cooper.springcache.book.domain.Book;
import com.cooper.springcache.book.domain.Phone;
import com.cooper.springcache.book.domain.PhoneRepository;
import com.cooper.springcache.book.dto.BookCreateRequestDto;
import com.cooper.springcache.book.dto.BookCreateResponseDto;
import com.cooper.springcache.book.dto.BookLookupResponseDto;
import com.cooper.springcache.book.dto.PhoneCreateRequestDto;
import com.cooper.springcache.book.dto.PhoneCreateResponseDto;
import com.cooper.springcache.book.dto.PhoneLookupResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PhoneService {

    private final PhoneRepository phoneRepository;

    public PhoneService(PhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;
    }

    public PhoneCreateResponseDto createBook(PhoneCreateRequestDto phoneCreateRequestDto) {
        Phone phone = Phone.create(phoneCreateRequestDto.getTitle());
        phoneRepository.save(phone);
        return new PhoneCreateResponseDto(phone.getId(), phone.getTitle());
    }

    @Cacheable(key = "#phoneId", cacheNames = {"phone"}, condition = "#phoneId != null")
    public PhoneLookupResponseDto findBookById(String phoneId) {
        log.debug("phoneId : {}", phoneId);
        Phone phone = phoneRepository.findById(phoneId).orElseThrow(RuntimeException::new);
        return new PhoneLookupResponseDto(phone.getId(), phone.getTitle());
    }

    @CacheEvict(key = "#phoneId", cacheNames = {"phone"})
    public void deleteBookById(String phoneId) {
        Phone phone = phoneRepository.findById(phoneId).orElseThrow(RuntimeException::new);
        phoneRepository.delete(phone);
    }

}
