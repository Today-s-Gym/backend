package com.todaysgym.todaysgym.member;

import com.todaysgym.todaysgym.avatar.Avatar;
import com.todaysgym.todaysgym.category.Category;
import com.todaysgym.todaysgym.post.Post;
import com.todaysgym.todaysgym.post.comment.Comment;
import com.todaysgym.todaysgym.record.Record;
import com.todaysgym.todaysgym.tag.Tag;
import com.todaysgym.todaysgym.utils.BaseTimeEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "member")
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    private String nickName;
    private String introduce;
    private String email;
    private String refreshToken;
    private String deviceToken;
    private boolean locked = false; // false = 공개, true = 비공개
    private int recordCount = 0;
    private boolean recordCheck = false;
    private int report = 0;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private Avatar avatar;

    @OneToMany(mappedBy = "member", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Record> recordList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Post> postList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Tag> tagList = new ArrayList<>();


    public void addRecordCount() {this.recordCount++;}
    public void updateRecordCheck(){this.recordCheck = true;}

    public void addReportCount() {
        this.report++;
    }

    public void changeAccountPrivacy(boolean locked) {
        this.locked = locked;
    }

    public void changeNickname(String newNickname) {
        this.nickName = newNickname;
    }

    public void editIntroduce(String introduce) {
        this.introduce = introduce;
    }
}