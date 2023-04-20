package com.example.books.client;

import com.example.books.models.Person;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PersonService {
    List<Person> findAll();

    void addPerson(Person person);

    boolean deletePersonById(Long id);

    Person showFormForUpdatePerson(Long id);

    void updatePerson(Person person);


}
