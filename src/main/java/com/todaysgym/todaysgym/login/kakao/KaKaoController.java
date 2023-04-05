package com.todaysgym.todaysgym.login.kakao;

import com.todaysgym.todaysgym.exception.BaseResponse;
import com.todaysgym.todaysgym.member.Member;
import com.todaysgym.todaysgym.member.MemberRepository;
import com.todaysgym.todaysgym.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class KaKaoController {

    private final KaKaoService kaKaoLoginService;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;


    //카카오 로그인 코드
    @ResponseBody
    @PostMapping("/oauth/kakao")
    public BaseResponse<?> kakaoCallback(@RequestParam("token") String accessToken) {
        try {
            //String accessToken = kaKaoLoginService.getAccessToken(code);
            String useremail = kaKaoLoginService.getUserInfo(accessToken);
            Optional<Member> findUser = memberRepository.findByEmail(useremail);


            if (findUser.isEmpty()) {
                //UserUpdateRequestDTO userUpdateRequestDTO = new UserUpdateRequestDTO(useremail);
                //User kakaoUser = userService.save(userUpdateRequestDTO);
                Member kakaoUser = new Member();
                kakaoUser.updateEmail(useremail);
                JwtResponseDTO.TokenInfo tokenInfo = jwtProvider.generateToken(kakaoUser.getUserId());
                kakaoUser.updateRefreshToken(tokenInfo.getRefreshToken());
                userRepository.save(kakaoUser);
                avatarService.setInitialAvatar(kakaoUser);

                // RefreshToken Redis 저장 (expirationTime 설정을 통해 자동 삭제 처리)
                //redisTemplate.opsForValue()
                //        .set("RT:" + useremail, tokenInfo.getRefreshToken(), jwtProvider.getExpiration(tokenInfo.getRefreshToken()), TimeUnit.MILLISECONDS);
                //userService.insertUser(kakaoUser);
                return new BaseResponse<>(tokenInfo);
            } else {
                User user = findUser.get();
                JwtResponseDTO.TokenInfo tokenInfo = jwtProvider.generateToken(user.getUserId());
                user.updateRefreshToken(tokenInfo.getRefreshToken());
                userRepository.save(user);
                //RefreshToken Redis 저장 (expirationTime 설정을 통해 자동 삭제 처리)
                //redisTemplate.opsForValue()
                //        .set("RT:" + useremail, tokenInfo.getRefreshToken(), jwtProvider.getExpiration(tokenInfo.getRefreshToken()), TimeUnit.MILLISECONDS);
                return new BaseResponse<>(tokenInfo);
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
