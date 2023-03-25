package com.todaysgym.todaysgym.secret;

public class Secret {
    public static String JWT_SECRET_KEY = "2b55382e29cd4e7aa90c069c6cfe74f6116c44170d5a2d91a7cb7d0ef658ddc5";

    public static long REFRESH_TOKEN_EXPIRE_TIME = 14 * 24 * 60 * 60 * 1000L; //refreshToken 유효기간 14일
    public static long ACCESS_TOKEN_EXPIRE_TIME = 1 * 6 * 60 * 60 * 1000L; //accessToken 유효기간 6시간
    public static String BEARER_TYPE = "Bearer";
}
