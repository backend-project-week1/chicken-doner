package com.supercoding.chickendoner.service;

import com.supercoding.chickendoner.dto.response.ChickenResponse;
import com.supercoding.chickendoner.entity.Chicken;
import com.supercoding.chickendoner.repository.ChickenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.mapper.Mapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChickenService {

    private final ChickenRepository chickenRepository;

    /*치킨 리스트 조회*/
    public List<ChickenResponse> getChickenList() {

        // 치킨 리스트를 엔티티객체로 받아옴
        List<Chicken> getList = chickenRepository.findAll();

        // List 바인딩
        // ChickenResponse 객체형태로 리스트를 생성(나중에 바이딩 할 예정)
        List<ChickenResponse> chickenResponseList = new ArrayList<>();

        // for 문을 통해 엔티티 객체를 response 객체로 바이딩하는 과정
        for (Chicken chicken : getList) {
            // 리스폰스객체 생성
            ChickenResponse chickenResponse = new ChickenResponse();
            // 리스폰스 내부 빌더를 통하여 바인딩
            chickenResponse = chickenResponse.chickenAllList(chicken);
            // 바인딩된 객체를 ResponseList 에 add
            chickenResponseList.add(chickenResponse);
        }

        // 바인딩 완료된 객체 반환
        return chickenResponseList;
    }
}
