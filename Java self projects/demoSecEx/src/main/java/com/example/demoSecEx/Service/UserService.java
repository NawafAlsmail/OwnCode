package com.example.demoSecEx.Service;

import com.example.demoSecEx.DTOs.UsersDTO;
import com.example.demoSecEx.UserRepo;
import com.example.demoSecEx.Model.Users;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepo repo;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authManager;

    private BCryptPasswordEncoder enc = new BCryptPasswordEncoder(12);

    public Users register(Users users) {
//        Users users = Users.build(0L, usersDTO.getUsername(),usersDTO.getPassword());
//        //Abdulrahman's method for linking the Entity and the DTO
//        Users.builder()
//                .id(0L)
//                .username(usersDTO.getUsername())
//                .build();
        users.setPassword(enc.encode(users.getPassword()));
        return repo.save(users);
    }

    public Users updateUser(Long id, UsersDTO usersDTO) {
        Users user = repo.findById(id).
                orElseThrow(() -> new UsernameNotFoundException("User with ID "+ id +" not found"));
        user.setPassword(enc.encode(user.getPassword()));
        return repo.save(user);
    }

    public Users getUser(Long id){
        return repo.findById(id).
                orElseThrow(() -> new UsernameNotFoundException("User with ID "+ id +" not found"));
    }

    public String verify(Users user) {
         Authentication authentication =
                authManager.authenticate(
                        new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        if (authentication.isAuthenticated())
            return jwtService.generateToken(user.getUsername());
        return "Failed";
    }
}
