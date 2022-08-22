package com.seo.myblog.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/*
* 댓글 Entity
* */
@Getter
@Setter
@ToString(exclude = {"content"})
@Entity
@Table(name = "comment")
public class Comment extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comment_id")
    private Long id; //댓글 id

    private String content; //댓글내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post; //댓글이 작성된 Post

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; //댓글 작성 Member

    public void updateComment(String content){
        this.content = content;
    }


}
