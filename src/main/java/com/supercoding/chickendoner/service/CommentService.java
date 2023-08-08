package com.supercoding.chickendoner.service;

import com.supercoding.chickendoner.dto.request.CommentRequest;
import com.supercoding.chickendoner.repository.CommentRepository;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private CommentRepository commentRepository;


    /*댓글 작성*/
    public Integer createComment(Long reviewId, CommentRequest commentRequest) {

        log.info("reviewId"+reviewId,"commentRequest"+commentRequest);

        return 1;
    }

}
