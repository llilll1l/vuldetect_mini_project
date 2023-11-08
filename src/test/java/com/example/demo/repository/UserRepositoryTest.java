package com.example.demo.repository;

import com.example.demo.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional

class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @Rollback(value = false)
    public void createUser() {
        User user1 = new User();
        user1.setName("mj");
        user1.setPassword("0000");

        User user2 = new User();
        user2.setName("sg");
        user2.setPassword("0000");

        userRepository.save(user1);
        userRepository.save(user2);

    }
}