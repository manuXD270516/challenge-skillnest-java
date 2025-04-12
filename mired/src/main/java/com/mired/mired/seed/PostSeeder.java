package com.mired.mired.seed;

import com.mired.mired.model.Post;
import com.mired.mired.model.User;
import com.mired.mired.repository.PostRepository;
import com.mired.mired.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(2)
public class PostSeeder implements CommandLineRunner {

    @Autowired
    private PostRepository postRepository;
    @Autowired private UserRepository userRepository;

    @Override
    public void run(String... args) {
        if (postRepository.count() == 0) {
            List<User> users = userRepository.findAll();

            for (int i = 1; i <= 10; i++) {
                Post post = new Post();
                post.setTitle("Publicación de prueba #" + i);
                post.setDescription("Esta es una descripción de la publicación " + i);
                post.setCategory(i % 2 == 0 ? "Tecnología" : "General");
                post.setImageUrl("https://picsum.photos/seed/" + i + "/400/300");
                post.setUserEmail(users.get(i % users.size()).getEmail());
                postRepository.save(post);
            }
        }
    }
}
