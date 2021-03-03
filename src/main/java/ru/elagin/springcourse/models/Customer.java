package ru.elagin.springcourse.models;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ru.elagin.springcourse.validation.UniqueTabnum;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@UniqueTabnum
public class Customer {

    private int id;

    @Min(value = 0, message = "Tabnum should not be negative")
    private int tabnum;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    @Pattern(regexp = "[а-яА-Яa-zA-z]+(\\s|-)?([а-яА-Яa-zA-z]+)?", message = "Name should not contains this the characters")
    private String name;

    @NotEmpty(message = "Surname should not be empty")
    @Size(min = 2, max = 30, message = "Surname should be between 2 and 30 characters")
    @Pattern(regexp = "[а-яА-Яa-zA-z]+(\\s|-)?([а-яА-Яa-zA-z]+)?", message = "Surname should not contains this the characters")
    private String surname;

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    private String email;

    @PastOrPresent(message = "Date should not be a future time")
    @NotNull(message = "Date should not be empty")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate birth;

}
