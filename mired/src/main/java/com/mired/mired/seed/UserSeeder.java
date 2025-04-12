package com.mired.mired.seed;

import com.mired.mired.model.Role;
import com.mired.mired.model.User;
import com.mired.mired.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class UserSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {
            for (int i = 1; i <= 10; i++) {
                User user = new User();
                user.setEmail("user" + i + "@mired.com");
                user.setPassword(passwordEncoder.encode("user" + i + "123"));
                user.setRole(i % 2 == 0 ? Role.ADMIN : Role.USER);
                userRepository.save(user);
            }
        }
    }
}
