package com.org.phone_book.repository;

import com.org.phone_book.dto.PhoneBookRequestDto;
import com.org.phone_book.dto.PhoneBookResponseDto;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PhoneBookRepositoryHiber {

    List<PhoneBookResponseDto> getAll();

    PhoneBookResponseDto getById(Long id);

    @Transactional
    Long create(PhoneBookRequestDto requestEntity);

    @Transactional
    boolean delete(Long id);

    @Transactional
    Long update(PhoneBookRequestDto requestEntity);
}
