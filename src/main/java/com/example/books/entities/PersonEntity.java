package com.example.books.entities;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Data
@AllArgsConstructor
public class PersonEntity {

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "First name should be between 2 and 30 characters")
    private String firstName;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Last name should be between 2 and 30 characters")
    private String lastName;


    @Override
    public String toString() {
        return "Person{ " +

                 firstName +
                ", " + lastName +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonEntity person)) return false;
        return new EqualsBuilder().append(getFirstName(), person.getFirstName()).append(getLastName(), person.getLastName()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getFirstName()).append(getLastName()).toHashCode();
    }
}
