package com.todaysgym.todaysgym.member;

import com.todaysgym.todaysgym.config.exception.BaseException;
import com.todaysgym.todaysgym.config.exception.errorCode.MemberErrorCode;
import com.todaysgym.todaysgym.member.dto.AccountPrivacyRes;
import com.todaysgym.todaysgym.utils.UtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final UtilService utilService;
    @Transactional //변경
    public void join(Member member) {
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
    }
    private void validateDuplicateMember(Member member) {
        Optional<Member> findMembers =
                memberRepository.findByNickName(member.getNickName());
        if (!findMembers.isEmpty()) {
            throw new BaseException(MemberErrorCode.ALREADY_EXIST);
        }
    }
    public Member findOne(Long memberId) {
        return memberRepository.getByMemberId(memberId).get();
    }

    @Transactional
    public Long changeAccountPrivacy(Long memberId, boolean locked) throws BaseException {
        Member member = utilService.findByMemberIdWithValidation(memberId);
        member.changeAccountPrivacy(locked);
        return member.getMemberId();
    }
    @Transactional(readOnly = true)
    public AccountPrivacyRes getMemberAccountPrivacy(Long memberId) {
        Member member = utilService.findByMemberIdWithValidation(memberId);
        return new AccountPrivacyRes(member.isLocked());
    }

    @Transactional(readOnly = true)
    public String findMemberEmailByUserId(long memberId) {
        Member member = utilService.findByMemberIdWithValidation(memberId);
        return member.getEmail();
    }
}