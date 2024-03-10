package com.example.books.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Borrowed {
    @JsonProperty("FirstName")
    public String firstName;

    @JsonProperty("LastName")
    public String lastName;

    @JsonProperty("From")
    @JsonFormat(pattern = "dd.MM.yyyy")
    private String from;


}
