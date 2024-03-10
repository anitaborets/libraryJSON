package com.example.books.book;

import com.example.books.BooksApplication;

import com.example.books.DTO.BookJSONDTO;
import com.example.books.DTO.Root;
import com.example.books.utils.FileWorker;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
public class BookRepositoryJSON {

    public ArrayList<BookJSONDTO> getAllFromFile() throws IOException {

        FileWorker fileWorker = new FileWorker();
        Root root = fileWorker.readFromFile();
        return Optional.ofNullable(root.library.getBook()).orElse(new ArrayList<>());
    }

    public void saveAllFromFile(ArrayList<BookJSONDTO> allBooks) throws IOException {

        FileWorker fileWorker = new FileWorker();
        Root root = fileWorker.readFromFile();

        root.library.setBook(allBooks);
        fileWorker.writeToFile(root);
    }

}
