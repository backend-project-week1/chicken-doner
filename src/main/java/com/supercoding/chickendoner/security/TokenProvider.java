package com.supercoding.chickendoner.security;

import com.supercoding.chickendoner.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
public class TokenProvider {
    private static final String SECRET_KEY = "c3VwZXJjb2Rpbmc=";

    //로그인 유저 토큰 생성 코드
    private String createToken(User user) {
        Date expiryDate = Date.from(
                Instant.now()
                        .plus(1, ChronoUnit.DAYS)
        );

        Claims claims = Jwts.claims();
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

    // 밝급된 Token이 만료 시간이 지났는지 체크
    public static boolean isExpired(String token) {
        Date expiredDate = extractClaims(token).getExpiration();
        // Token의 만료 날짜가 지금보다 이전인지 check
        return expiredDate.before(new Date());
    }

    // SecretKey를 사용해 Token Parsing
    private static Claims extractClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

}
