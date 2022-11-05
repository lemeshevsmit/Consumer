package com.mycompany.app.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

public class ActivePojo {

    @Pattern(message = "Bad formed name: ${validatedValue} must contains: a", regexp = ".*[a,A].*")
    @Length(min = 7, message = "name length must be greater than or equal to {min}")
    private String name;

    @Min(value = 10)
    private Long count;

    @PastOrPresent
    private LocalDate createdAt;


    public ActivePojo() {
        //Constructor without parameters
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "ActivePojo{" +
                "name='" + name + '\'' +
                ", count=" + count +
                ", createdAt=" + createdAt +
                '}';
    }
}
