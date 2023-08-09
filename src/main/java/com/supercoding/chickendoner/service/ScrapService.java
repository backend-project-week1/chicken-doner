package com.supercoding.chickendoner.service;

import com.supercoding.chickendoner.common.Error.CustomException;
import com.supercoding.chickendoner.common.Error.ErrorCode;
import com.supercoding.chickendoner.entity.Chicken;
import com.supercoding.chickendoner.entity.Scrap;
import com.supercoding.chickendoner.entity.User;
import com.supercoding.chickendoner.repository.ChickenRepository;
import com.supercoding.chickendoner.repository.ScrapRepository;
import com.supercoding.chickendoner.repository.UserRepository;
import com.supercoding.chickendoner.security.Auth;
import com.supercoding.chickendoner.security.AuthHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ScrapService {

    private final ChickenRepository chickenRepository;
    private final ScrapRepository scrapRepository;
    private final UserRepository userRepository;


    // Todo 1. 장바구니 추가
    @Transactional
    public void saveScrap(Long id, Long userIdx) {

        // 스크랩의 치킨 아이디와 유저 아이디를 통해 조회. 같은 것이면 amount + 1;
        // 등록시에는 리스트로 나올 수 없다. 별개 아이템.

          /*
        1) (치킨 아이디와 유저 아이디로) 스크랩이 존재하는 경우: 유저가 해당 치킨 등록을 했다는 것.
             -> 해당 치킨 스크랩에 amount + 1 추가

        2) 스크랩(장바구니)가 존재하지 않는 경우: 유저가 아직 해당 치킨의 등록을 하지 않았음.
             -> 해당 치킨 등록을 한다
        */

        //치킨 레포에서 요청의 치킨 아이디를 넣고 치킨 엔티티를 찾는다 : 없으면 NOTFOUND_ITEM
        Chicken chicken = chickenRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOTFOUND_ITEM));

        //유저를 불러옴, 유저는 userIdx를 넣어서 레포에서 찾은 것.
        User user = userRepository.findById(userIdx).orElseThrow(() -> new CustomException(ErrorCode.NOTFOUND_USER));

        //스크랩(장바구니) 존재하는 지 여부
        Boolean isScrapPresent = scrapRepository.existsByChickenIdAndUserId(chicken.getId(), user.getId());

        if (isScrapPresent) {
            //스크랩 이미 존재 시,
            Scrap scrapfound = scrapRepository.findScrapByChickenIdAndUserId(chicken.getId(), user.getId());

            //기존 스크랩의 amount에 + 1 추가
            scrapfound.setAmount(scrapfound.getAmount() + 1);

            scrapfound.setIsDeleted(false);

        } else {
            //없으면 장바구니(스크랩) 새로 생성
            Scrap newScrap = Scrap.builder()
                    .chicken(chicken)
                    .user(user)
                    .amount(1L)
                    .isDeleted(false) //처음에 새로 생성했을 때 isDeleted(false)
                 // .createdAt(Timestamp.valueOf(LocalDateTime.now())) 안 넣어도 됨.
                 //  엔티티의  @CreationTimestamp 으로 자동 생성.
                    .build();

            scrapRepository.save(newScrap);

        }
    }

    // Todo 2. 장바구니 비우기

    @Transactional
    public void deleteScrap(Long id, Long userIdx) {

        //요청하는 아이템(치킨)을 레포에서 치킨 아이디로 찾는다 : 없으면 NOTFOUND_ITEM
        Chicken chicken = chickenRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NOTFOUND_ITEM));

        //유저를 불러옴, 유저는 userIdx를 넣어서 레포에서 찾은 것.
        User user = userRepository.findById(userIdx).orElseThrow(() -> new CustomException(ErrorCode.NOTFOUND_USER));

        //스크랩(장바구니) 존재하는 지 여부
        Boolean isScrapPresent = scrapRepository.existsByChickenIdAndUserId(chicken.getId(), user.getId());

        
        if (isScrapPresent){
            //스크랩 이미 존재 시,

            Scrap scrapfound = scrapRepository.findScrapByChickenIdAndUserId(chicken.getId(), user.getId());

            //스크랩 amount > 1 -> 스크랩 amount - 1
            if (scrapfound.getAmount() > 1) {
                scrapfound.setAmount(scrapfound.getAmount() - 1);
            //  scrapfound.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

            //스크랩 amount = 1 -> 스크랩 amount - 1, isDeleted(true)

            } else if (scrapfound.getAmount() == 1){
                scrapfound.setAmount(scrapfound.getAmount() - 1);
                scrapfound.setIsDeleted(true);
            //  scrapfound.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
            //  굳이 설정 안해줘도 scrap 엔티티의 @UpdateTimestamp로 자동 업데이트 됨.

            } else throw new CustomException(ErrorCode.REQUEST_INVALID); //스크랩 amount = 0인 상태에서 요청보낼 때 에러.

        } else throw new CustomException(ErrorCode.NOTFOUND_ITEM); //스크랩에 없는 치킨(아이템)을 비우려고 할 때 에러.


    }
}








