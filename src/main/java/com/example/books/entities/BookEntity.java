package com.example.books.entities;

import com.example.books.DTO.Borrowed;
import com.example.books.models.Author;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookEntity {
    private String name;

    private Author author;
    private boolean isBorrowed = false;
    public BookEntity(String name, Author author) {
        this.name = name;
        this.author = author;
    }




}
