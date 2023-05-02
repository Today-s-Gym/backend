package com.todaysgym.todaysgym.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetMyPageRes {
    private String avatarImgUrl;
    private String nickname;
    private String categoryName;
    private String introduce;
    private MemberRecordCount memberRecordCount;
    private boolean locked;

    public GetMyPageRes(String avatarImgUrl, String nickname, String categoryName, String introduce, boolean locked) {
        this.avatarImgUrl = avatarImgUrl;
        this.nickname = nickname;
        this.categoryName = categoryName;
        this.introduce = introduce;
        this.locked = locked;
    }

    private GetMyPageRes(boolean locked) {
        this.locked = locked;
    }

    public static GetMyPageRes lockedMyPageInfo() {
        return new GetMyPageRes(true);
    }

    public void setMemberRecordCount(MemberRecordCount memberRecordCount) {
        this.memberRecordCount = memberRecordCount;
    }
}
