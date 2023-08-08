package com.supercoding.chickendoner.service;

import com.supercoding.chickendoner.entity.User;
import com.supercoding.chickendoner.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getLoginUser(String username) {
        return userRepository.findByUsername(username);
    }
}
