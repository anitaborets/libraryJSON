package com.example.books.client;

import com.example.books.models.Book;
import com.example.books.models.Person;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PersonService {
    List<Person> findAll();
    Page<Person> findAllWithPagination(Integer page, Integer size);
    void addPerson(Person person);

    boolean deletePersonById(Long id);

    Person showFormForUpdatePerson(Long id);

    void updatePerson(Person person);


}
