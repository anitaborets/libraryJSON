package com.example.books.client;

import com.example.books.models.Book;
import com.example.books.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    //TODO return list if email exists
    @Transactional(readOnly = true)
    Optional<Person> findByEmail(String email);

    @Transactional(readOnly = true)
    @Override
    Optional<Person> findById(Long id);
;
}
