package com.example.books;


import com.example.books.DTO.BookJSONDTO;
import com.example.books.DTO.Root;
import com.example.books.book.BookServiceJSONImpl;
import com.example.books.entities.BookEntity;
import com.example.books.entities.PersonEntity;
import com.example.books.utils.FileWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.util.Pair;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

//@SpringBootApplication
//@EnableJpaRepositories
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@Slf4j
public class BooksApplication {

    public static void main(String[] args) {
        SpringApplication.run(BooksApplication.class, args);

        log.warn("Server started...");

    }

}
