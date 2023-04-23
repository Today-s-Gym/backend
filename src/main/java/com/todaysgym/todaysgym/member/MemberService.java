package com.todaysgym.todaysgym.member;

import static com.todaysgym.todaysgym.config.exception.errorCode.MemberErrorCode.EMPTY_USER_CATEGORY;
import static com.todaysgym.todaysgym.config.exception.errorCode.MemberErrorCode.EMPTY_USER_NICKNAME;

import com.todaysgym.todaysgym.category.Category;
import com.todaysgym.todaysgym.config.exception.BaseException;
import com.todaysgym.todaysgym.config.exception.errorCode.MemberErrorCode;
import com.todaysgym.todaysgym.member.dto.AccountPrivacyRes;
import com.todaysgym.todaysgym.member.dto.GetMyPageRes;
import com.todaysgym.todaysgym.member.dto.MemberRecordCount;
import com.todaysgym.todaysgym.record.RecordRepository;
import com.todaysgym.todaysgym.utils.UtilService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final UtilService utilService;
    private final RecordRepository recordRepository;
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

    @Transactional
    public GetMyPageRes getMyPage(Long memberId) {
        Member member = utilService.findByMemberIdWithValidation(memberId);
        validateUserNicknameAndCategory(member.getNickName(), member.getCategory());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        String thisMonth = LocalDate.now().format(formatter);

        int thisMonthRecordCount = recordRepository.countByMemberIdMonth(member.getMemberId(), thisMonth);
        int totalRecordCount = member.getRecordCount();

        GetMyPageRes myPageInfo = new GetMyPageRes(
            member.getAvatar().getImgUrl(),
            member.getNickName(),
            member.getCategory().getName(),
            member.getIntroduce(),
            member.isLocked());

        MemberRecordCount memberRecordCount = new MemberRecordCount(
            thisMonthRecordCount,
            member.getAvatar().getRemainUpgradeCount(totalRecordCount),
            totalRecordCount);

        myPageInfo.setMemberRecordCount(memberRecordCount);
        return myPageInfo;
    }

    private void validateUserNicknameAndCategory(String nickName, Category category) {
        if (nickName == null) {
            throw new BaseException(EMPTY_USER_NICKNAME);
        }
        if (category == null) {
            throw new BaseException(EMPTY_USER_CATEGORY);
        }
    }
}