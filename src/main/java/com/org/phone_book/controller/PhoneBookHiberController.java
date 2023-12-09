package com.org.phone_book.controller;

import com.org.phone_book.dto.PhoneBookRequestDto;
import com.org.phone_book.dto.PhoneBookResponseDto;
import com.org.phone_book.repository.PhoneBookRepositoryHiber;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/hiber/phone-book-entries")
public class PhoneBookHiberController {

    private final PhoneBookRepositoryHiber phoneBookRepository;

    @GetMapping("/get-all")
    public List<PhoneBookResponseDto> getPhoneBookEntries(){
        return phoneBookRepository.getAll();
    }

    @PostMapping("/add")
    public Long addNewEntries(@Validated @RequestBody PhoneBookRequestDto requestDto){
        return phoneBookRepository.create(requestDto);
    }

    @GetMapping("/get")
    public PhoneBookResponseDto getPhoneBookEntryById(@RequestParam Long id){
        return phoneBookRepository.getById(id);
    }

    @DeleteMapping("/delete")
    public boolean deletePhoneBookEntryById(@RequestParam Long id){
        return phoneBookRepository.delete(id);
    }

    @PutMapping("/put")
    public Long updatePhoneBookEntryById(@Validated @RequestBody PhoneBookRequestDto requestDto,
                                         @RequestParam Long id){
        requestDto.setId(id);
        return phoneBookRepository.update(requestDto);
    }
}
