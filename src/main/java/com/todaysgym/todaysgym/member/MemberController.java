package com.todaysgym.todaysgym.member;

import static com.todaysgym.todaysgym.config.exception.errorCode.MemberErrorCode.MEMBER_PRIVACY_REQUEST_ERROR;

import com.todaysgym.todaysgym.config.exception.BaseException;
import com.todaysgym.todaysgym.config.response.BaseResponse;
import com.todaysgym.todaysgym.login.jwt.JwtService;
import com.todaysgym.todaysgym.member.dto.AccountPrivacyReq;
import com.todaysgym.todaysgym.member.dto.AccountPrivacyRes;
import com.todaysgym.todaysgym.member.dto.MemberEmailRes;
import io.swagger.annotations.ApiOperation;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final JwtService jwtService;

    @ApiOperation(value = "사용자 공개 계정 전환하기")
    @PutMapping("/user/locked")
    public BaseResponse<Long> changeAccountPrivacy(@RequestBody AccountPrivacyReq accountPrivacyReq) {
        Long memberId = jwtService.getMemberIdx();
        Set<String> values = Set.of("true", "True", "TRUE", "false", "False", "FALSE");
        if (!values.contains(accountPrivacyReq.getLocked())) {
            throw new BaseException(MEMBER_PRIVACY_REQUEST_ERROR);
        }
        return new BaseResponse<>(memberService.changeAccountPrivacy(memberId,
            Boolean.parseBoolean(accountPrivacyReq.getLocked())));
    }

    @ApiOperation(value = "사용자 공개 계정 여부 확인하기")
    @GetMapping("/user/check-locked")
    public BaseResponse<AccountPrivacyRes> getMemberAccountPrivacy() {
        Long memberId = jwtService.getMemberIdx();
        return new BaseResponse<>(memberService.getMemberAccountPrivacy(memberId));
    }

    @GetMapping("/user/email")
    public BaseResponse<MemberEmailRes> getUserEmail() {
        String userEmail = memberService.findMemberEmailByUserId(jwtService.getMemberIdx());
        MemberEmailRes memberEmailRes = new MemberEmailRes(userEmail);
        return new BaseResponse<>(memberEmailRes);
    }
}
