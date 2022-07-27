package com.seo.myblog.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "head_img")
public class HeadImg {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "head_img_id")
    private Long id; //기본키

    private String imgName; //헤드 이미지 파일명(서버에 저장된 파일명)

    private String orgImgName; //원본 이미지 파일명

    private String imgUrl; //이미지 조회 경로

    @OneToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
