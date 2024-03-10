package com.example.books.DTO;

import com.example.books.book.BookState;
import com.example.books.models.Person;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookJSONDTO implements Serializable {

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 15, message = "Name should be between 2 and 15 characters")
    @JsonProperty("Name")
    private String name;

    @NotEmpty(message = "Name should not be empty")
    @JsonProperty("Author")
    private String author;

    //todo validation in future
   // private Date bookingDate;

    @JsonProperty("Borrowed")
    Borrowed borrowed;




}
