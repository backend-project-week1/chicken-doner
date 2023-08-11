package com.supercoding.chickendoner.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "장바구니 요청 DTO")
public class ScrapRequest {

    @Schema(description = "치킨 ID 필드")
    private Long id;
}
