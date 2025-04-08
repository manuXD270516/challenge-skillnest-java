package com.mired.mired.seed;
import com.mired.mired.model.Post;
import com.mired.mired.model.Role;
import com.mired.mired.model.User;
import com.mired.mired.repository.PostRepository;
import com.mired.mired.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired private UserRepository userRepository;
    @Autowired private PostRepository postRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            User admin = new User();
            admin.setEmail("admin@mired.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ADMIN);

            User user = new User();
            user.setEmail("usuario@mired.com");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setRole(Role.USER);

            userRepository.save(admin);
            userRepository.save(user);
        }

        if (postRepository.count() == 0) {
            Post post1 = new Post();
            post1.setTitle("Bienvenido a MiRed");
            post1.setDescription("Esta es una publicación inicial del sistema.");
            post1.setCategory("General");
            post1.setImageUrl("https://via.placeholder.com/400");
            post1.setUserEmail("admin@mired.com");

            Post post2 = new Post();
            post2.setTitle("Primera publicación del usuario");
            post2.setDescription("Publicación de prueba generada automáticamente.");
            post2.setCategory("Prueba");
            post2.setImageUrl("https://via.placeholder.com/400");
            post2.setUserEmail("usuario@mired.com");

            postRepository.save(post1);
            postRepository.save(post2);
        }
    }
}
