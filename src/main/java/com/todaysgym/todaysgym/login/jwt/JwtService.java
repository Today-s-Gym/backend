package com.todaysgym.todaysgym.login.jwt;

import com.todaysgym.todaysgym.config.exception.BaseException;
import com.todaysgym.todaysgym.secret.Secret;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;

import static com.todaysgym.todaysgym.config.exception.errorCode.AuthErrorCode.*;

@RequiredArgsConstructor
@Service
public class JwtService {
    //코드 사용x
    private final Secret secret = new Secret();
    private Key key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(Secret.JWT_SECRET_KEY));
    /**
     * Header에서 Authorization 으로 JWT 추출
     */
    public String getJwt(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("Authorization");
    }

    /*
    JWT에서 memberId 추출
     */
    public long getMemberIdx() throws BaseException {

        //1. JWT 추출
        String accessToken = getJwt();
        if(accessToken == null || accessToken.length() == 0){
            throw new BaseException(EMPTY_JWT);
        }

        // 2. JWT parsing
        Jws<Claims> claims;
        try{
            claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken);
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            throw new BaseException(INVALID_JWT);
        } catch (ExpiredJwtException e) {
            throw new BaseException(EXPIRED_MEMBER_JWT);
        } catch (Exception ignored) {
            throw new BaseException(INVALID_JWT);
        }

        // 3. memberId 추출
        return claims.getBody().get("memberId",long.class);
    }

    public long getMemberIdWithJWT(String accessToken) throws BaseException {

        //1. JWT 추출
        if(accessToken == null || accessToken.length() == 0){
            throw new BaseException(EMPTY_JWT);
        }

        // 2. JWT parsing
        Jws<Claims> claims;
        try{
            claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken);
        }catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            throw new BaseException(INVALID_JWT);
        } catch (ExpiredJwtException e) {
            throw new BaseException(EXPIRED_MEMBER_JWT);
        } catch (Exception ignored) {
            throw new BaseException(INVALID_JWT);
        }

        // 3. memberId 추출
        return claims.getBody().get("memberId",long.class);
    }
}
