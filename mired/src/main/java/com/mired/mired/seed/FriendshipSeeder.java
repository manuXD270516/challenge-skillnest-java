package com.mired.mired.seed;

import com.mired.mired.model.FriendRequest;
import com.mired.mired.model.User;
import com.mired.mired.repository.FriendRequestRepository;
import com.mired.mired.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(3)
public class FriendshipSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FriendRequestRepository friendRequestRepository;

    @Override
    public void run(String... args) {
        if (friendRequestRepository.count() == 0) {
            List<User> users = userRepository.findAll();

            for (int i = 0; i < users.size(); i++) {
                for (int j = i + 1; j < users.size(); j++) {
                    if ((i + j) % 3 == 0) {
                        FriendRequest request = new FriendRequest();
                        request.setSender(users.get(i));
                        request.setReceiver(users.get(j));
                        request.setAccepted((i + j) % 2 == 0);
                        friendRequestRepository.save(request);
                    }
                }
            }
        }
    }
}
