package com.supercoding.chickendoner.service;

import com.supercoding.chickendoner.common.Error.CustomException;
import com.supercoding.chickendoner.common.Error.ErrorCode;
import com.supercoding.chickendoner.dto.request.UserDetailRequest;
import com.supercoding.chickendoner.entity.User;
import com.supercoding.chickendoner.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public boolean checkLoginIdDuplicate(String username) {
        boolean checkDupId = userRepository.existsByUsername(username);
        if (checkDupId) {
            throw new CustomException(ErrorCode.USERNAME_DUPLICATION);
        } else {
            return false;
        }
    }

    public String signUp(UserDetailRequest userDetailRequest) {
        User signUpUser = userRepository.save(userDetailRequest.toEntity(passwordEncoder.encode(userDetailRequest.getPassword())));
        return signUpUser.getUsername();
    }

    public User getLoginUser(String username) {
        if(username == null){
            throw new CustomException(ErrorCode.LOGIN_INPUT_INVALID);
        }

        Optional<User> optionalUser = userRepository.findByUsername(username);
        if(optionalUser.isEmpty()){
            throw new CustomException(ErrorCode.LOGIN_INPUT_INVALID);
        }

        return optionalUser.get();
    }
}
