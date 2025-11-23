package com.example.demoSecEx.Controller;

import com.example.demoSecEx.DTOs.UsersDTO;
import com.example.demoSecEx.Service.UserService;
import com.example.demoSecEx.Model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



@RestController
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping("/register")
    public Users register(@Validated(UsersDTO.OnCreate.class) @RequestBody UsersDTO usersDTO) {
        Users users = new Users();
        users.setUsername(usersDTO.getUsername());
        users.setPassword(usersDTO.getPassword());
        return service.register(users);
    }
    @PutMapping("/update/{id}")
    public Users updateUser(@Validated(UsersDTO.OnUpdate.class)
                                @PathVariable Long id, @RequestBody UsersDTO usersDTO) {
        return service.updateUser(id, usersDTO);
    }
    
    @PostMapping("/login")
    public String login(@RequestBody Users user) {
        return service.verify(user);
    }

    @RequestMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id){
        return ResponseEntity.ok(service.getUser(id));
    }
}
