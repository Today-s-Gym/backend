package com.todaysgym.todaysgym.member;

import static com.todaysgym.todaysgym.config.exception.errorCode.MemberErrorCode.EMPTY_USER_CATEGORY;
import static com.todaysgym.todaysgym.config.exception.errorCode.MemberErrorCode.EMPTY_USER_NICKNAME;
import static com.todaysgym.todaysgym.config.exception.errorCode.MemberErrorCode.LENGTH_OVER_INTRODUCE;
import static com.todaysgym.todaysgym.config.exception.errorCode.MemberErrorCode.NICKNAME_ERROR;

import com.todaysgym.todaysgym.avatar.Avatar;
import com.todaysgym.todaysgym.category.Category;
import com.todaysgym.todaysgym.config.exception.BaseException;
import com.todaysgym.todaysgym.config.exception.errorCode.MemberErrorCode;
import com.todaysgym.todaysgym.config.response.BaseResponse;
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
    private static int INTRODUCE_MAX_LENGTH = 30;
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

    @Transactional(readOnly = true)
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

    @Transactional(readOnly = true)
    public GetMyPageRes getMemberProfile(Long memberId) {
        Member member = utilService.findByMemberIdWithValidation(memberId);
        if (member.isLocked()) {
            return GetMyPageRes.lockedMyPageInfo();
        }
        return getMyPage(memberId);
    }

    @Transactional
    public BaseResponse<Long> editMyPage(Long memberId, String newNickname, String newIntroduce) throws BaseException {
        Member member = utilService.findByMemberIdWithValidation(memberId);
        editNickName(newNickname, member);
        editIntroduce(newIntroduce, member);
        return new BaseResponse<>(member.getMemberId());
    }

    private void editNickName(String newNickname, Member member) throws BaseException {
        if (member.getNickName().equals(newNickname)) {
            return;
        }
        if (memberRepository.findByNickName(newNickname).isPresent()) {
            throw new BaseException(NICKNAME_ERROR);
        }
        member.changeNickname(newNickname);
    }

    private static void editIntroduce(String newIntroduce, Member member) throws BaseException {
        if (newIntroduce.length() > INTRODUCE_MAX_LENGTH) {
            throw new BaseException(LENGTH_OVER_INTRODUCE);
        }
        member.editIntroduce(newIntroduce);
    }

    @Transactional(readOnly = true)
    public String getNowAvatar(Long memberId) throws BaseException {
        Member member = utilService.findByMemberIdWithValidation(memberId);
        return member.getAvatar().getImgUrl();
    }

    @Transactional
    public boolean checkAndMyAvatarLevelUp(Long memberId) {
        Member member = utilService.findByMemberIdWithValidation(memberId);
        int recordCount = member.getRecordCount();
        Avatar avatar = Avatar.findByRecordCount(recordCount);

        if (!member.getAvatar().equals(avatar)) {
            memberLevelUp(member, avatar);
            return true;
        }
        return false;
    }

    @Transactional
    public void memberLevelUp(Member member, Avatar avatar) {
        member.changeAvatar(avatar);
    }
}