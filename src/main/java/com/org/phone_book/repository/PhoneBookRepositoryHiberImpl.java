package com.org.phone_book.repository;

import com.org.phone_book.dto.PhoneBookRequestDto;
import com.org.phone_book.dto.PhoneBookResponseDto;
import com.org.phone_book.entity.PhoneBook;
import com.org.phone_book.exception.PhoneBookEntriesNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class PhoneBookRepositoryHiberImpl implements PhoneBookRepositoryHiber {

    private final ModelMapper modelMapper;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<PhoneBookResponseDto> getAll() {
        var session = entityManager.unwrap(Session.class);

        TypedQuery<PhoneBook> query = session.createQuery("FROM PhoneBook", PhoneBook.class);
        List<PhoneBook> allEntities = query.getResultList();

        session.close();

        return allEntities.stream()
                .map(o -> modelMapper.map(o, PhoneBookResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public PhoneBookResponseDto getById(Long id) {
        var session = entityManager.unwrap(Session.class);

        PhoneBook entity = session.get(PhoneBook.class, id);

        session.close();

        return modelMapper.map(entity, PhoneBookResponseDto.class);
    }

    @Override
    public Long create(PhoneBookRequestDto requestEntity) {
        var session = entityManager.unwrap(Session.class);

        Long entityId = session.merge(modelMapper.map(requestEntity, PhoneBook.class)).getId();

        session.close();

        return entityId;
    }

    @Override
    public boolean delete(Long id) {
        var session = entityManager.unwrap(Session.class);

        var currentEntity = findById(id, session);

        session.remove(currentEntity);
        session.close();

        return currentEntity.getId() >= 0;
    }

    @Override
    public Long update(PhoneBookRequestDto requestEntity) {
        var session = entityManager.unwrap(Session.class);
        PhoneBook foundedById;

        try {
            foundedById = findById(requestEntity.getId(), session);
            modelMapper.map(requestEntity, foundedById);
            session.merge(foundedById);

            session.flush();
        } catch (OptimisticLockException ex) {
            throw new OptimisticLockException("Another user already has been modifying entity with id " + requestEntity.getId(), ex);
        } finally {
            session.close();
        }

        return foundedById.getId();
    }

    private PhoneBook findById(Long id, Session session) {
        var foundedEntity = session.find(PhoneBook.class, id);

        if (foundedEntity == null)
            throw new PhoneBookEntriesNotFoundException("Can't find entries with id " + id);

        return foundedEntity;
    }
}
