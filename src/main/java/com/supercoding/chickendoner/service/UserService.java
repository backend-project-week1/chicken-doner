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
import com.supercoding.chickendoner.dto.response.ReviewResponse;
import com.supercoding.chickendoner.dto.response.UserDetailResponse;
import com.supercoding.chickendoner.entity.Chicken;
import com.supercoding.chickendoner.entity.Review;
import com.supercoding.chickendoner.entity.Scrap;
import com.supercoding.chickendoner.entity.User;
import com.supercoding.chickendoner.repository.LikeRepository;
import com.supercoding.chickendoner.repository.ReviewRepository;
import com.supercoding.chickendoner.repository.ScrapRepository;
import com.supercoding.chickendoner.repository.UserRepository;
import com.supercoding.chickendoner.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
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

    private final ReviewRepository reviewRepository;
    private final LikeRepository likeRepository;


    public boolean checkLoginIdDuplicate(String username) {
        boolean checkDupId = userRepository.existsByUsername(username);
        if (checkDupId) {
            throw new CustomException(ErrorCode.USERNAME_DUPLICATION);
        } else {
            return false;
        }
    }

    public String signUp(UserDetailRequest userDetailRequest) {
        User signUpUser = userRepository.save(
            userDetailRequest.toEntity(passwordEncoder.encode(userDetailRequest.getPassword())));
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
        if (username == null) {
            throw new CustomException(ErrorCode.LOGIN_INPUT_INVALID);
        }

        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new CustomException(ErrorCode.LOGIN_INPUT_INVALID);
        }

        return optionalUser.get();
    }

    @Transactional
    public String deleteUser(Long userIdx) {
        if (userIdx == null) {
            throw new CustomException(ErrorCode.LOGIN_INPUT_INVALID);
        }
        User user = userRepository.findById(userIdx)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_AUTHORIZED));
        userRepository.deleteById(user.getId());
        return user.getUsername();
    }

    public UserDetailResponse getMyProfile(Long userIdx) throws ParseException {
        if (userIdx == null) {
            throw new CustomException(ErrorCode.LOGIN_INPUT_INVALID);
        }

        Optional<User> loginUser = userRepository.findById(userIdx);
        if (loginUser.isEmpty()) {
            throw new CustomException(ErrorCode.LOGIN_INPUT_INVALID);
        }

        UserDetailResponse userDetailResponse = new UserDetailResponse();
        return userDetailResponse.toResponse(loginUser.get());
    }

    public void patchMyProfile(UserUpdateRequest userUpdateRequest, Long userIdx) {
        if (userIdx == null) {
            throw new CustomException(ErrorCode.LOGIN_INPUT_INVALID);
        }

        User originUser = userRepository.findById(userIdx)
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_AUTHORIZED));
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

    public List<MyScrapResponse> findAllScraps(Long userIdx) throws ParseException {

        //유저의 아이디 널값 여부
        if (userIdx == null) {
            throw new CustomException(ErrorCode.LOGIN_INPUT_INVALID);
        }

        //로그인된 유저 불러옴
        Optional<User> loginUser = userRepository.findById(userIdx);
        if (loginUser.isEmpty()) {
            throw new CustomException(ErrorCode.LOGIN_INPUT_INVALID);
        }

        //userId와 boolean 값을 넣어서 isDeleted = false 인 스크랩들만 찾아오기
        List<Scrap> scraps = scrapRepository.findAllByIsDeletedEqualsAndUserId(false, userIdx,
            Sort.by(Sort.Direction.DESC, "createdAt"));

        //스크랩 디티오 리스트
        List<MyScrapResponse> myScrapResponses = new ArrayList<>();

        //스크립 리스트 내용이 없으면
        if (scraps.isEmpty()) {
            return null;
        } else { // 스크랩 리스트에 내용이 있을 때


        /* myScraps: 스크랩의 isDeleted가 false인 스크랩들만 모음 (아래 코드는 느리기 때문에 레포에서 쿼리로 가져오는 게 빠름)
        List<Scrap> myScraps = scraps.stream().filter(scrap -> !scrap.getIsDeleted()).collect(Collectors.toList()); */

            //myScrap(엔티티)마다 myScrapResponse(디티오)로 변환해주는 로직
            for (Scrap scrap : scraps) {

                //chickenScrap(디티오)는 scrap(엔티티)를 ChickenScrap(디티오)의 toChickenScrap 메소드에 넣고 반환된 디티오
                ChickenScrap chickenScrap = new ChickenScrap();
                ChickenScrap chickenScrap1 = chickenScrap.toChickenScrap(scrap);

                //myScrapResponse(디티오)는 chickenScrap(디티오)를 MyScrapResponse(디티오)의 toMyScrapResponse 메소드에 넣고 맞춰준 디티오.
                MyScrapResponse myScrapResponse = new MyScrapResponse();
                MyScrapResponse myScrapResponse1 = myScrapResponse.toMyScrapResponse(chickenScrap1);

                //myScrapResponse(최종 디티오)를 myScrapResponse 리스트에 넣어준다.
                myScrapResponses.add(myScrapResponse1);

            }
            //myScrapResponse 리스트 반환.
            return myScrapResponses;
        }
    }

    //<유저 서비스>
    public List<ReviewResponse> getMyReviews(Long userIdx) {//로그인 유저의 id를 요청받음
        //유저의 아이디 널값 여부
        if (userIdx == null) {
            throw new CustomException(ErrorCode.LOGIN_INPUT_INVALID);
        }

        //로그인된 유저 불러옴
        Optional<User> loginUser = userRepository.findById(userIdx);
        if (loginUser.isEmpty()) {
            throw new CustomException(ErrorCode.LOGIN_INPUT_INVALID);
        }

        //userId와 boolean 값을 넣어서 isDeleted = false 인 리뷰들만 찾아오기
        List<Review> reviews = reviewRepository.findAllByIsDeletedEqualsAndUserId(false, userIdx,Sort.by(Sort.Direction.DESC, "createAt"));

        //reviewResponse(디티오)
        ReviewResponse reviewResponse = new ReviewResponse();

        return reviews.stream()
            .map((review)->{
                        try {
                            Long likeCount = likeRepository.countByIsDeletedFalseAndReview_Id(review.getId());
                            return reviewResponse.getMyReview(review, likeCount);
                        } catch (ParseException e) {
                            throw new CustomException(ErrorCode.CONVERTING_FAILED);
                        }
                    })
            .collect(Collectors.toList());
    }
}
















