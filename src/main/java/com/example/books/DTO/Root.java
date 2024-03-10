package com.example.books.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

public class Root {
    @JsonProperty("Library")
    public Library library;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class Library {
        @JsonProperty("Book")
        public ArrayList<BookJSONDTO> book;

    }


}
