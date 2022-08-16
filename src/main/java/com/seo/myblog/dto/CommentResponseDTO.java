package com.seo.myblog.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/*
* 댓글 보기 기능 위한 DTO
* */
@Getter
@Setter
@ToString
public class CommentResponseDTO {
    
    private Long commentId; //댓글번호
    
    private String content; //댓글내용
    
    private String commentDate; //작성시간
    
    private String memberNick; //댓글 작성 회원 닉네임
    
    private String memberAvatar; //회원 avatar url

}
