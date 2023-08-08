package com.supercoding.chickendoner.security;

import com.supercoding.chickendoner.common.Error.CustomException;
import com.supercoding.chickendoner.common.Error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Component
public class AuthInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Auth authAnnotation = handlerMethod.getMethodAnnotation(Auth.class);

            if (authAnnotation != null) {
                boolean includeUserIdx = authAnnotation.includeUserIdx();
                if (includeUserIdx) {

                    if (!request.getHeader("Authorization").startsWith("Bearer ")) {
                        throw new CustomException(ErrorCode.HANDLE_ACCESS_DENIED);
                    }
                    String token = request.getHeader("Authorization").split(" ")[1];

                    Long userIdx = TokenProvider.getLoginIdx(token);
                    request.setAttribute("userIdx", userIdx);
                    AuthHolder.setUserIdx(userIdx);
                }
                return true;
            }
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    // JWT 토큰 검증 로직을 여기에 구현

}
