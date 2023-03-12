package com.todaysgym.todaysgym.user;

import com.todaysgym.todaysgym.exception.BaseResponse;
import com.todaysgym.todaysgym.exception.BaseResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional //변경
    public void join(Member member) {
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
    }

    private BaseResponse<?> validateDuplicateMember(Member member) {
        Optional<Member> findMembers =
                memberRepository.findByNickName(member.getNickName());
        if (!findMembers.isEmpty()) {
            return new BaseResponse<>(BaseResponseStatus.Already_Exist);
        }
        else return new BaseResponse<>(BaseResponseStatus.SUCCESS);
    }

    public Member findOne(Long memberId) {
        return memberRepository.getByMemberId(memberId).get();
    }
}
