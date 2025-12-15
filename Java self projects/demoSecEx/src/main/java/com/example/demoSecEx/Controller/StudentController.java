package com.example.demoSecEx.Controller;

import com.example.demoSecEx.DTOs.StudentDTO;
import com.example.demoSecEx.Model.Student;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class StudentController {
        private List<Student> students = new ArrayList<>(List.of(
                new Student("nawaf", 50, 1),
                new Student("khaled", 60, 2)
        ));

        private AtomicInteger idCounter = new AtomicInteger();


    @GetMapping("/students")
    public List<Student> getStudents() {
        return students;
    }

    @PostMapping("/students")
    public ResponseEntity<?> addStudent(@RequestBody @Valid StudentDTO studentDTO) {
        Student newStudent = new Student();
        newStudent.setName(studentDTO.getName());
        newStudent.setMark(studentDTO.getMark());
        newStudent.setId(idCounter.getAndIncrement()+2);
        students.add(newStudent);
        return ResponseEntity.ok(students);
    }

//    @RequestMapping("/csrf")
//    public CsrfToken getCsrfToken(HttpServletRequest request) {
//        return (CsrfToken) request.getAttribute("_csrf");
//    }
}
