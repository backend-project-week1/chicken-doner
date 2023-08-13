package com.supercoding.chickendoner.service;

import com.supercoding.chickendoner.common.Error.CustomException;
import com.supercoding.chickendoner.common.Error.ErrorCode;
import com.supercoding.chickendoner.entity.Scrap;
import com.supercoding.chickendoner.entity.User;
import com.supercoding.chickendoner.repository.ScrapRepository;
import com.supercoding.chickendoner.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final ScrapRepository scrapRepository;
    private final UserRepository userRepository;

    public void doOrder(Long userIdx) {

        User user =  userRepository.findById(userIdx).orElseThrow(() -> new CustomException(ErrorCode.NOTFOUND_USER));

        List<Scrap> scrapListByuUserIdx = scrapRepository.findAllByIsDeletedEqualsAndUserId(false, user.getId(), Sort.by("createdAt"));




    }

}
