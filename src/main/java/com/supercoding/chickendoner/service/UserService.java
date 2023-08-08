package com.supercoding.chickendoner.service;

import com.supercoding.chickendoner.common.Error.CustomException;
import com.supercoding.chickendoner.common.Error.ErrorCode;
import com.supercoding.chickendoner.dto.request.LoginRequest;
import com.supercoding.chickendoner.dto.request.UserDetailRequest;
import com.supercoding.chickendoner.dto.response.UserDetailResponse;
import com.supercoding.chickendoner.entity.User;
import com.supercoding.chickendoner.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
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

    public User login(LoginRequest loginRequest) {
        Optional<User> optionalUser = userRepository.findByUsername(loginRequest.getUsername());

        if (optionalUser.isEmpty()) {
            throw new CustomException(ErrorCode.LOGIN_INPUT_INVALID);
        }
        User loginUser = optionalUser.get();

        if (!passwordEncoder.matches(loginRequest.getPassword(), loginUser.getPassword())) {
            throw new CustomException(ErrorCode.LOGIN_INPUT_INVALID);
        }

        return loginUser;
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

    public UserDetailResponse getMyProfile(String username) throws ParseException {
        if(username == null){
            throw new CustomException(ErrorCode.LOGIN_INPUT_INVALID);
        }

        Optional<User> loginUser = userRepository.findByUsername(username);
        if(loginUser.isEmpty()){
            throw new CustomException(ErrorCode.LOGIN_INPUT_INVALID);
        }

        UserDetailResponse userDetailResponse = new UserDetailResponse();
        return userDetailResponse.toResponse(loginUser.get());

    }
}
