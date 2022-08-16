package com.seo.myblog.controller;


import com.seo.myblog.dto.CommentDTO;
import com.seo.myblog.dto.UserInfoDTO;
import com.seo.myblog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/*
* 댓글 기능 관련 컨트롤러
* */
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /*
    * 포스트 쓰기 시도
    * */
    @PostMapping("/comment")
    public ResponseEntity write(@RequestBody CommentDTO commentDTO, @AuthenticationPrincipal UserInfoDTO userInfoDTO) {

        Long commentId;

        try {
            commentId = commentService.writeComment(commentDTO, userInfoDTO.getNick());
        } catch (Exception e){
            return new ResponseEntity<String>("오류로 인해 댓글 작성에 실패하였습니다", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<Long>(commentId,HttpStatus.OK);
    }
}
