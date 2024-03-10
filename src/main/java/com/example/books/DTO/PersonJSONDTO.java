package com.example.books.DTO;

import com.example.books.models.Book;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.Cascade;

import java.util.Set;

@Data
@AllArgsConstructor
public class PersonJSONDTO {

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "First name should be between 2 and 30 characters")
    private String firstName;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Last name should be between 2 and 30 characters")
    private String lastName;


    @Override
    public String toString() {
        return "Person{" +

                "name='" + firstName + '\'' +
                ", surName='" + lastName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonJSONDTO person)) return false;
        return new EqualsBuilder().append(getFirstName(), person.getFirstName()).append(getLastName(), person.getLastName()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getFirstName()).append(getLastName()).toHashCode();
    }
}
