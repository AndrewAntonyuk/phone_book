package com.org.phone_book.dto;

import lombok.Data;

@Data
public class PhoneBookResponseDto {

    private Long id;

    private String firstName;

    private String middleName;

    private String lastName;

    private String description;

    private String phone;
}
