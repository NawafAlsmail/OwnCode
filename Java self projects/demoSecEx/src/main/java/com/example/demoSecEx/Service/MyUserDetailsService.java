package com.example.demoSecEx.Service;

import com.example.demoSecEx.Model.UserPrincipal;
import com.example.demoSecEx.UserRepo;
import com.example.demoSecEx.Model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user = repo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));

        if (user == null) {
            throw new UsernameNotFoundException("Username not found");
        }
        return new UserPrincipal(user);
    }

    public boolean userExists(String username) {
        return repo.findByUsername(username).isPresent();
    }

    public Users loadUserByUsernameNullable(String username) {
        try {
            return (Users) loadUserByUsername(username); // your existing method
        } catch (Exception e) {
            return null;
        }
    }

    // these help creating new admin if not exists
    public boolean existsByUsername(String username) {
        return repo.findByUsername(username).isPresent();
    }

    public void createUser(String username, String password) {
        if (existsByUsername(username)) return;
        Users user = new Users();
        user.setUsername(username);
        user.setPassword(new BCryptPasswordEncoder(12).encode(password));
        repo.save(user);
    }
}
