package com.example.demoSecEx.DTOs;

import com.example.demoSecEx.Model.Users;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UsersDTO{

    public interface OnCreate{}
    public interface OnUpdate{}

    @NotBlank(groups = OnCreate.class, message = "Username can't be blank")
    @Size(groups = OnCreate.class,
            min = 3, max = 20, message = "the username must be between 3 to 20")
    private String username;

    @NotBlank(groups = {OnCreate.class, OnUpdate.class},
            message = "Password can't be blank")
    @Pattern(groups = {OnCreate.class, OnUpdate.class},
            regexp="^.{8,16}$",
            message = "password has to be at least 8 characters but not more than 16")
    private String password;
}
