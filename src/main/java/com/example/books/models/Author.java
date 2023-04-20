package com.example.books.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    private String name;

}
