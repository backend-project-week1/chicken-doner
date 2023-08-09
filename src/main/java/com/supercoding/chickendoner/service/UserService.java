package com.supercoding.chickendoner.service;

import com.supercoding.chickendoner.common.Error.CustomException;
import com.supercoding.chickendoner.common.Error.ErrorCode;
import com.supercoding.chickendoner.dto.request.LoginRequest;
import com.supercoding.chickendoner.dto.request.UserDetailRequest;
import com.supercoding.chickendoner.dto.request.UserRequest;
import com.supercoding.chickendoner.dto.request.UserUpdateRequest;
import com.supercoding.chickendoner.dto.response.LoginResponse;
import com.supercoding.chickendoner.dto.response.UserDetailResponse;
import com.supercoding.chickendoner.entity.User;
import com.supercoding.chickendoner.repository.UserRepository;
import com.supercoding.chickendoner.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Optional;

@Service
@Slf4j
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

    public UserDetailResponse getMyProfile(Long userIdx) throws ParseException {
        if(userIdx == null){
            throw new CustomException(ErrorCode.LOGIN_INPUT_INVALID);
        }

        Optional<User> loginUser = userRepository.findById(userIdx);
        if(loginUser.isEmpty()){
            throw new CustomException(ErrorCode.LOGIN_INPUT_INVALID);
        }

        UserDetailResponse userDetailResponse = new UserDetailResponse();
        return userDetailResponse.toResponse(loginUser.get());
    }

    public void patchMyProfile(UserUpdateRequest userUpdateRequest, Long userIdx) {
        if(userIdx == null){
            throw new CustomException(ErrorCode.LOGIN_INPUT_INVALID);
        }

        User originUser = userRepository.findById(userIdx).orElseThrow(()->new CustomException(ErrorCode.NOT_AUTHORIZED));
        originUser = userUpdateRequest.updateEntity(userUpdateRequest, originUser);

        userRepository.save(originUser);

    }

    public LoginResponse makeLoginResp(User loginUser) {
        String jwtToken = TokenProvider.createToken(loginUser);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUsername(loginUser.getUsername());
        loginResponse.setUserIdx(loginUser.getId());
        loginResponse.setAccessToken(jwtToken);

        return loginResponse;
    }
}
