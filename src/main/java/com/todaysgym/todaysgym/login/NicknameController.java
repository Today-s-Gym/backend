package com.todaysgym.todaysgym.login;


import com.todaysgym.todaysgym.config.exception.BaseException;
import com.todaysgym.todaysgym.config.exception.errorCode.MemberErrorCode;
import com.todaysgym.todaysgym.member.Member;
import com.todaysgym.todaysgym.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class NicknameController {



    private final MemberRepository userRepository;

    @GetMapping("/login/nickname")
    @ResponseBody
    public String updateNickname(@RequestParam("nickname") String nickname)
    {
        Optional<Member> findUser = userRepository.findByNickName(nickname);
        if(findUser.isEmpty()){
            return nickname;
        }
        else{
            throw new BaseException(MemberErrorCode.NICKNAME_ERROR);
        }
    }
}

