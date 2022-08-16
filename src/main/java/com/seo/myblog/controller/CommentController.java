package com.seo.myblog.controller;


import com.seo.myblog.dto.CommentDTO;
import com.seo.myblog.dto.CommentResponseDTO;
import com.seo.myblog.dto.UserInfoDTO;
import com.seo.myblog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/*
* 댓글 기능 관련 컨트롤러
* */
@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /*
    * 댓글 쓰기 시도
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

    /*
    * 댓글 목록 보기
    * */
    @GetMapping("/comment/all")
    public ResponseEntity getAllComment(Optional<Long> postId){
        //postId 지정x일 경우 에러 반환
        if(postId.isEmpty()){
            return new ResponseEntity<String>("댓글 목록 반환 실패",HttpStatus.BAD_REQUEST);
        }

        List<CommentResponseDTO> commentResponseDTOList = commentService.getAllComments(postId.get());

        return new ResponseEntity<List<CommentResponseDTO>>(commentResponseDTOList,HttpStatus.OK);
    }
}
