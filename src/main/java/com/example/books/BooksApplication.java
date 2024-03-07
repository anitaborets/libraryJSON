package com.example.books;


import com.example.books.DTO.BookJSONDTO;
import com.example.books.DTO.Root;
import com.example.books.book.BookRepositoryJSON;
import com.example.books.entities.BookEntity;
import com.example.books.entities.PersonEntity;
import com.example.books.models.Author;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.util.Pair;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@SpringBootApplication
@EnableJpaRepositories
@Slf4j
public class BooksApplication {

    @Autowired
    static BookRepositoryJSON bookRepositoryJSON;

    public static void main(String[] args) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
//todo si nevsimajte :}


        objectMapper.registerModule(new JavaTimeModule());

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        log.warn("Server started...");
        File file = new File("src/main/resources/library.json");

        Root root = objectMapper.readValue(file, Root.class);

        Map<String, Object> map = objectMapper.readValue(file, new TypeReference<>() {
        });

        List<BookJSONDTO> allBooks = root.library.getBook();

        Set<BookEntity> library = new HashSet<>();


        HashMap<BookEntity, Pair<PersonEntity, LocalDate>> reservations = new HashMap<>();


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");


        for (BookJSONDTO book : allBooks) {
            BookEntity bookEntity = new BookEntity(book.getName(), new Author(book.getAuthor()));
            if (book.getBorrowed() != null) {
                bookEntity.setBorrowed(true);

                PersonEntity person = new PersonEntity(book.getBorrowed().getFirstName(), book.getBorrowed().getLastName());
                LocalDate dateFrom = book.getBorrowed().getFrom().isEmpty() ? null : LocalDate.parse(book.getBorrowed().getFrom(), formatter);

                if(dateFrom != null){
                    Pair<PersonEntity, LocalDate> pair = Pair.of(person, dateFrom);
                    reservations.putIfAbsent(bookEntity, pair);
                }

            }

            library.add(bookEntity);
        }

            //library = books.stream().collect(Collectors.toMap(book -> book, book -> new HashMap<>()));


            for (Map.Entry<BookEntity, Pair<PersonEntity, LocalDate>> entry : reservations.entrySet()) {
                System.out.println(entry.getKey() + " : " + entry.getValue());
            }
        }





}
