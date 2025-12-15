package com.example.demoSecEx.Security;

import com.example.demoSecEx.Model.Users;
import com.example.demoSecEx.Service.JWTService;
import com.example.demoSecEx.Service.MyUserDetailsService;
import com.example.demoSecEx.UserRepo;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

//this class is helping for creating a dummy admin for the JWT token access tp the swagger
@Component
public class AdminTokenStableKey {

    private final JWTService jwtService;
    private final MyUserDetailsService userDetailsService;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public AdminTokenStableKey(JWTService jwtService, MyUserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void creatingAdminToken() {
        String adminUsername = "admin";
        String adminPassword = "admin123";

        // ✅ Only create admin if it does NOT exist
        Users admin = userDetailsService.loadUserByUsernameNullable(adminUsername);

        if (admin == null) {
            // admin does not exist -> create it
            userDetailsService.createUser(adminUsername, adminPassword);
            System.out.println("Dummy admin user created in DB");
        } else {
            System.out.println("Admin user already exists");
        }

        // ✅ Generate a token for this admin
        String token = jwtService.generateToken(adminUsername);

        System.out.println("\n========== DEV ADMIN JWT ==========");
        System.out.println("username: " + adminUsername);
        System.out.println("password: " + adminPassword);
        System.out.println("Bearer " + token);
        System.out.println("==================================\n");
    }
}