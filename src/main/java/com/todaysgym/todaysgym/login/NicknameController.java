package com.todaysgym.todaysgym.login;

import com.todaysgym.todaysgym.exception.BaseResponse;
import com.todaysgym.todaysgym.exception.BaseResponseStatus;
import com.todaysgym.todaysgym.user.Member;
import com.todaysgym.todaysgym.user.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class NicknameController {


    private final MemberRepository userRepository;

    @GetMapping("/login/nickname")
    //@ResponseBody
    //public BaseResponse<?> updateNickname(@RequestParam("nickname") String nickName)
    public BaseResponse<?> updateNickname(String nickName)
    {
        Optional<Member> findUser = userRepository.findByNickName(nickName);
        if(findUser.isEmpty()){
            return new BaseResponse<>(BaseResponseStatus.SUCCESS);
        }
        else{
            return new BaseResponse<>(BaseResponseStatus.NICKNAME_ERROR);
        }
    }
}

