package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    //회원가입
    public Long join(User user) {
    validateDuplicateUser(user);
        userRepository.save(user);
        return user.getId();
    }

    private void validateDuplicateUser(User user) {
        List<User> findUser =  userRepository.findByName(user.getName());
        if(!findUser.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }

    }

    public User findUserByName(String name) {
        User user = userRepository.findByName(name).get(0);
        return user;
    }

    //회원 전체 조회
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    //회원 id로 최원 찾기
    public User findOne(Long id){
        return userRepository.findOne(id);
    }


    public List<User> findName(String name) {
        return userRepository.findByName(name);

    }
}
