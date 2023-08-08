package com.supercoding.chickendoner.security;

import com.supercoding.chickendoner.common.Error.CustomException;
import com.supercoding.chickendoner.common.Error.ErrorCode;
import com.supercoding.chickendoner.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class TokenProvider {
    private static final String SECRET_KEY = "c3VwZXJjb2Rpbmc=";

    //로그인 유저 토큰 생성 코드
    public static String createToken(User user) {
        Date expiryDate = Date.from(
                Instant.now()
                        .plus(1, ChronoUnit.DAYS)
        );

        Claims claims = Jwts.claims();
        claims.put("userIdx", user.getId());
        claims.put("username", user.getUsername());

        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .setClaims(claims)
                .setExpiration(expiryDate)
                .compact();
    }

    // Claims에서 loginId 꺼내기
    public static String getLoginId(String token) {
        return extractClaims(token).get("username").toString();
    }

    public static Long getLoginIdx(String token) {
        return Long.valueOf(extractClaims(token).get("userIdx").toString());
    }

    // 밝급된 Token이 만료 시간이 지났는지 체크
    public static boolean isExpired(String token) {
        try {
            Date expiredDate = extractClaims(token).getExpiration();
            // Token의 만료 날짜가 지금보다 이전인지 check
            return expiredDate.before(new Date());
        } catch (RuntimeException e) {
            throw new CustomException(ErrorCode.LOGIN_INPUT_INVALID);
        }

    }

    // SecretKey를 사용해 Token Parsing
    private static Claims extractClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

}
