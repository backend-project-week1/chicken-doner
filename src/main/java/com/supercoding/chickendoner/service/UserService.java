package com.supercoding.chickendoner.service;

import com.supercoding.chickendoner.common.Error.CustomException;
import com.supercoding.chickendoner.common.Error.ErrorCode;
import com.supercoding.chickendoner.dto.request.LoginRequest;
import com.supercoding.chickendoner.dto.response.ChickenScrap;
import com.supercoding.chickendoner.dto.response.MyScrapResponse;
import com.supercoding.chickendoner.dto.request.UserDetailRequest;
import com.supercoding.chickendoner.dto.request.UserRequest;
import com.supercoding.chickendoner.dto.request.UserUpdateRequest;
import com.supercoding.chickendoner.dto.response.LoginResponse;
import com.supercoding.chickendoner.dto.response.UserDetailResponse;
import com.supercoding.chickendoner.entity.Chicken;
import com.supercoding.chickendoner.entity.Scrap;
import com.supercoding.chickendoner.entity.User;
import com.supercoding.chickendoner.repository.ScrapRepository;
import com.supercoding.chickendoner.repository.UserRepository;
import com.supercoding.chickendoner.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ScrapRepository scrapRepository;


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

    @Transactional
    public String deleteUser(Long userIdx) {
        if(userIdx == null){
            throw new CustomException(ErrorCode.LOGIN_INPUT_INVALID);
        }
        User user = userRepository.findById(userIdx).orElseThrow(()->new CustomException(ErrorCode.NOT_AUTHORIZED));
//        userRepository.deleteUser(user.getId());
        userRepository.deleteById(user.getId());
        return user.getUsername();
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

    public List<MyScrapResponse> findAllScraps(Long userIdx) throws ParseException{

        //유저의 아이디 널값 여부
        if(userIdx == null){
            throw new CustomException(ErrorCode.LOGIN_INPUT_INVALID);
        }

        //로그인된 유저 불러옴
        Optional<User> loginUser = userRepository.findById(userIdx);
        if(loginUser.isEmpty()){
            throw new CustomException(ErrorCode.LOGIN_INPUT_INVALID);
        }

//        //유저
//        User user = userRepository.findById(userIdx).orElseThrow(() -> new CustomException(ErrorCode.NOTFOUND_USER));

        //스크랩 리스트
        List<Scrap> scraps = scrapRepository.findAllByIsDeletedEqualsAndUserId(false, userIdx);

        //스크랩 디티오 리스트
        List<MyScrapResponse> myScrapResponses = new ArrayList<>();

        //스크립 리스트 내용이 없으면
        if (scraps.isEmpty()) {
            return null;
        } else { // 스크랩 리스트에 내용이 있을 때

        //myScraps: 스크랩의 isDeleted가 false인 스크랩들만 모음
//            List<Scrap> myScraps = scraps.stream().filter(scrap -> !scrap.getIsDeleted()).collect(Collectors.toList());

        //myScrap(엔티티)마다 myScrapResponse(디티오)로 변환해주는 로직
            for(Scrap scrap: scraps) {


                ChickenScrap chickenScrap = ChickenScrap.builder()
                        .chickenName(scrap.getChicken().getChikenName()) // 치킨 엔티티의 이름 필드를 가져와 설정
                        .content(scrap.getChicken().getContent())   // 치킨 엔티티의 content 필드를 가져와 설정
                        .price(scrap.getChicken().getPrice())       // 치킨 엔티티의 price 필드를 가져와 설정
                        .build();

                MyScrapResponse myScrapResponse = MyScrapResponse.builder()
                        .chickenScrap(chickenScrap)
                        .build();


                myScrapResponses.add(myScrapResponse);

        }

            return myScrapResponses;
        }
    }


}



















