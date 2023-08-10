package com.supercoding.chickendoner.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.supercoding.chickendoner.common.Error.ErrorCode;
import com.supercoding.chickendoner.common.util.ApiUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            setErrorResponse(response, ErrorCode.CANT_ACCESS);
        } catch (AccessDeniedException e) {
            setErrorResponse(response, ErrorCode.HANDLE_ACCESS_DENIED);
        } catch (JwtException e) {
            filterChain.doFilter(request, response);
        }
    }

    private <T> void setErrorResponse(HttpServletResponse response, ErrorCode errorCode) {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(errorCode.getStatus());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        CommonResponse<T> customException = ApiUtils.fail(false, errorCode.getStatus(), errorCode.getMessage());
        try {
            response.getWriter().write(objectMapper.writeValueAsString(customException));
        } catch (IOException e) {
           e.printStackTrace();
        }
    }
}
