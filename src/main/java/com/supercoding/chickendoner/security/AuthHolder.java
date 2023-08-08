package com.supercoding.chickendoner.security;

public class AuthHolder {
    private static final ThreadLocal<Long> userIdxHolder = new ThreadLocal<>();

    public static void setUserIdx(Long userIdx) {
        userIdxHolder.set(userIdx);
    }

    public static Long getUserIdx() {
        return userIdxHolder.get();
    }

    public static void clearUserIdx() {
        userIdxHolder.remove();
    }
}
