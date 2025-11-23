package com.example.demoSecEx.Model;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Student {

    private String name;
    private int mark;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
}
