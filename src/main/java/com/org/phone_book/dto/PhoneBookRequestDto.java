package com.org.phone_book.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PhoneBookRequestDto {

    private Long id;

    @Size(min = 3, max = 100, message = "Value must be in range 3 - 100")
    private String firstName;

    @Size(max = 100, message = "Value must be in range 0 - 100")
    private String middleName;

    @Size(max = 100, message = "Value must be in range 0 - 100")
    private String lastName;

    @Size(max = 200, message = "Value must be in range 0 - 200")
    private String description;

    @Pattern(regexp = "^(\\+\\d{1,2}\\s?)?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$", message = "Incorrect phone number")
    private String phone;
}
