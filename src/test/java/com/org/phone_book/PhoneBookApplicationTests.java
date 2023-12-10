package com.org.phone_book;

import com.org.phone_book.dto.PhoneBookRequestDto;
import com.org.phone_book.repository.PhoneBookRepository;
import jakarta.persistence.OptimisticLockException;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PhoneBookApplicationTests {
    @Autowired
    PhoneBookRepository repository;

    @Autowired
    PhoneBookRepository repository1;

    @Autowired
    ModelMapper modelMapper;

    @Test
    void contextLoads() {
        PhoneBookRequestDto newEntity = new PhoneBookRequestDto();
        newEntity.setFirstName("TestName");
        newEntity.setPhone("0123456789");

        var idNewEntity = repository.create(newEntity);

        var createdEntity = repository.getById(idNewEntity);

        assertEquals(createdEntity.getPhone(), "0123456789");
    }

    @Test
    void testCreate() {
        PhoneBookRequestDto newEntity = new PhoneBookRequestDto();
        newEntity.setFirstName("TestName");
        newEntity.setPhone("0123456789");

        var idNewEntity = repository.create(newEntity);

        var createdEntity = repository.getById(idNewEntity);

        assertEquals(createdEntity.getPhone(), "0123456789");
    }

    @Test
    void testConcurrencyUpdate() throws InterruptedException {
        PhoneBookRequestDto newEntity = new PhoneBookRequestDto();
        newEntity.setFirstName("TestName");
        newEntity.setPhone("0123456789");

        var idNewEntity = repository.create(newEntity);

        ExecutorService executor = Executors.newFixedThreadPool(2);

        PhoneBookRequestDto updatedEntity1 = new PhoneBookRequestDto();
        updatedEntity1.setFirstName("TestName");
        updatedEntity1.setPhone("2589631470");
        updatedEntity1.setId(idNewEntity);

        PhoneBookRequestDto updatedEntity2 = new PhoneBookRequestDto();
        updatedEntity2.setFirstName("Ddddd");
        updatedEntity2.setPhone("9876543210");
        updatedEntity2.setId(idNewEntity);

        try {
            executor.execute(() -> repository.update(updatedEntity1));
            executor.execute(() -> repository1.update(updatedEntity2));

            executor.shutdown();
            executor.awaitTermination(1, TimeUnit.MINUTES);

            final var item = repository.getById(idNewEntity);
        } catch (OptimisticLockException ex) {
            assertEquals(ex.getCause(), "OptimisticLockException");
            throw new OptimisticLockException(ex);
        } finally {
            executor.shutdown();
            try {
                executor.awaitTermination(1, TimeUnit.MINUTES);
            } finally {
                executor.shutdownNow();
            }
        }
    }
}
