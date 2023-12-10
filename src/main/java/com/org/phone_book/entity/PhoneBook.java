package com.org.phone_book.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "phone_book")
@Data
public class PhoneBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    private String middleName;

    private String lastName;

    private String description;

    @Column(nullable = false)
    private String phone;

    @Version
    private Long version;
}
