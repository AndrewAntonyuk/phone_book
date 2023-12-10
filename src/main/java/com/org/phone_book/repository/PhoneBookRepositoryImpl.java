package com.org.phone_book.repository;

import com.org.phone_book.dto.PhoneBookRequestDto;
import com.org.phone_book.dto.PhoneBookResponseDto;
import com.org.phone_book.entity.PhoneBook;
import com.org.phone_book.exception.PhoneBookEntriesNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class PhoneBookRepositoryImpl implements PhoneBookRepository {

    private final ModelMapper modelMapper;

    //private final ReentrantLock lock;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<PhoneBookResponseDto> getAll() {
        List<PhoneBook> allEntities = entityManager.createQuery("FROM PhoneBook", PhoneBook.class)
                .getResultList();

        return allEntities.stream()
                .map(o -> modelMapper.map(o, PhoneBookResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public PhoneBookResponseDto getById(Long id) {
        return modelMapper.map(findById(id), PhoneBookResponseDto.class);
    }

    @Override
    public Long create(PhoneBookRequestDto requestEntity) {
        return entityManager.merge(modelMapper.map(requestEntity, PhoneBook.class)).getId();
    }

    @Override
    public boolean delete(Long id) {
        var currentEntity = findById(id);

        entityManager.remove(currentEntity);

        return currentEntity.getId() >= 0;
    }

    @Override
    public Long update(PhoneBookRequestDto requestEntity) {
        //region lock() with ReentrantLock
        //lock.lock();
        //PhoneBook byId;
        //try{
        //byId = findById(requestEntity.getId());
        //entityManager.merge(modelMapper.map(requestEntity, PhoneBook.class));
        //} finally {
        //    lock.unlock();
        //}
        //endregion

        PhoneBook foundedById;

        try {
            foundedById = findById(requestEntity.getId());

            modelMapper.map(requestEntity, foundedById);
            entityManager.merge(foundedById);

            entityManager.flush();
        } catch (OptimisticLockException ex) {
            throw new OptimisticLockException("Another user already has been modifying entity with id " + requestEntity.getId(), ex);
        }

        return foundedById.getId();
    }

    private PhoneBook findById(Long id) {
        var foundedEntity = entityManager.find(PhoneBook.class, id);

        if (foundedEntity == null)
            throw new PhoneBookEntriesNotFoundException("Can't find entries with id " + id);

        return foundedEntity;
    }
}
