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

    private String fileName; //첨부파일명(서버에 저장된 파일명)

    private String orgFileName; //원본 첨부파일명

    private String fileUrl; //첨부파일 조회 경로

    @OneToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
