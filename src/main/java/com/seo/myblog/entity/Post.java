package com.seo.myblog.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter @Setter @ToString
@Entity @Table(name = "post")
public class Post extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_id")
    private Long id; //기본키

    private String title; //제목

    private String hook_text; //요약

    @Lob
    private String content; //본문
    
    private String tags; //태그

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category; //카테고리

    public void updatePost(String title, String hook_text, String content, String tags, Category category){
        this.title = title;
        this.hook_text = hook_text;
        this.content = content;
        this.tags = tags;
        this.category = category;
    }

}
