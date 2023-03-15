package com.todaysgym.todaysgym.record;

import com.todaysgym.todaysgym.member.Member;
import com.todaysgym.todaysgym.post.Post;
import com.todaysgym.todaysgym.record.photo.RecordPhoto;
import com.todaysgym.todaysgym.tag.Tag;
import com.todaysgym.todaysgym.utils.BaseTimeEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "record")
public class Record extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordId;

    private String content;
    private int report = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "record", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Post> postList = new ArrayList<>();

    @OneToMany(mappedBy = "record", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<RecordPhoto> photoList = new ArrayList<>();

    @OneToMany(mappedBy = "record", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Tag> tagList = new ArrayList<>();
}