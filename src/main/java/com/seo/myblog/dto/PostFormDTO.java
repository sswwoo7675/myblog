package com.seo.myblog.dto;

import com.seo.myblog.entity.Post;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class PostFormDTO {

    @NotBlank(message = "게시글 제목은 필수 입력값입니다.")
    String title; //게시글 제목

    String hook_text; //게시글 요약

    String content; //글 본분

    Long categoryId; //카테고리 번호

    String tags; //글 태그

    List<String> contentImgs  = new ArrayList<>(); //본문에 포함된 이미지 경로

    MultipartFile headImgFile; //헤드 이미지 파일

    MultipartFile uploadFile; //첨부파일

    private static ModelMapper modelMapper = new ModelMapper();

    //DTO => Entity
    public Post createPost(){
        return modelMapper.map(this, Post.class);
    }

}
