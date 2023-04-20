package com.example.books.client;

import com.example.books.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    PersonRepository personRepository;

    @Override
    public List<Person> findAll() {
        List clients;
        try {
            clients = personRepository.findAll();
        } catch (Exception e) {
            clients = Collections.EMPTY_LIST;
        }
        return clients;
    }

    @Override
    @Transactional
    public void addPerson(Person person) {
        Person personForSave = personRepository.findByEmail(person.getEmail()).orElseGet(() -> personRepository.save(person));
    }

    @Override
    @Transactional
    public boolean deletePersonById(Long id) {
        boolean isDeleted = false;
        Optional<Person> optional = personRepository.findById(id);
        if (optional.isPresent()) {
            Person person = optional.get();
            if (person.getPersonCard().isEmpty()) {
                try {
                    personRepository.deleteById((long) id);
                    isDeleted = true;
                } catch (Exception e) {
                    //TODO logger and alert
                    System.out.println("SO");
                }
            }
        }
        return isDeleted;
    }

    @Override
    public Person showFormForUpdatePerson(Long id) {
        Optional<Person> personForUpdate = personRepository.findById((long) id);
        return personForUpdate.orElse(null);
    }

    @Override
    public void updatePerson(Person person) {
        try {
            personRepository.save(person);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
    }
}
