package com.example.demoSecEx.DTOs;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
@Data
public class StudentDTO {
    @NotBlank(message = "The name can't be blank")
    private String name;
    @Min(value = 50, message = "The mark can't be less than 50")
    @Max(value = 100, message = "The mark can't be more than 100")
    private int mark;
}
