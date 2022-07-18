package com.seo.myblog.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "attached_file")
public class AttachedFile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "attached_file_id")
    private Long id; //기본키

    private String imgName; //헤드 이미지 파일명(서버에 저장된 파일명)

    private String orgImgName; //원본 이미지 파일명

    private String imgUrl; //이미지 조회 경로

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
