package com.example.books.client;

import com.example.books.models.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PersonServiceImpl implements PersonService {

    @Autowired(required = false)
    PersonRepository personRepository;

    @Override
    public List<Person> findAll() {
        List clients;
        try {
            clients = personRepository.findAll(Sort.by("name"));
        } catch (Exception e) {
            clients = Collections.EMPTY_LIST;
        }
        return clients;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Person> findAllWithPagination(Integer page, Integer size) {
        if (page >= 0 && size >= 0) {
            Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
            return personRepository.findAll(pageable);
        } else {
            return personRepository.findAll(PageRequest.of(1, 3, Sort.by("name")));
        }
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
                    log.warn("person was not deleted. id: " + person.getId() + " because has books " + e.getMessage());
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
            log.warn("person was not updated. id: " + person.getId() + e.getMessage());
            return;
        }
    }
}
