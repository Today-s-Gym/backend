package com.todaysgym.todaysgym.login;


import com.todaysgym.todaysgym.exception.BaseResponse;
import com.todaysgym.todaysgym.exception.BaseResponseStatus;
import com.todaysgym.todaysgym.member.Member;
import com.todaysgym.todaysgym.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
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
    public BaseResponse<?> updateNickname(@RequestParam("nickname") String nickname)
    {
        Optional<Member> findUser = userRepository.findByNickName(nickname);
        if(findUser.isEmpty()){
            return new BaseResponse<>(BaseResponseStatus.SUCCESS);
        }
        else{
            return new BaseResponse<>(BaseResponseStatus.NICKNAME_ERROR);
        }
    }
}

