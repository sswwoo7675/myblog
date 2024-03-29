package com.seo.myblog.service;

import com.seo.myblog.Repository.CommentRepository;
import com.seo.myblog.dto.CommentDTO;
import com.seo.myblog.dto.CommentResponseDTO;
import com.seo.myblog.entity.Comment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@Transactional
//@TestPropertySource(locations = "classpath:application-test.properties")
class CommentServiceTest {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    CommentService commentService;

    @Test
    void writeComment() throws Exception{
        //82번 포스트에 운영자임으로 작성시도
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setContent("랄랄라랄");
        commentDTO.setPostId(82L);

        Long savedCommentId = commentService.writeComment(commentDTO,"운영자임");

        //저장된 comment 불러오기
        Comment savedComment = commentRepository.getById(savedCommentId);

        //저장된 comment에서 postId와 member의 nick 불러오기
        Long postId = savedComment.getPost().getId();
        String memberNick = savedComment.getMember().getNick();
        
        //값 검증
        assertEquals("랄랄라랄",savedComment.getContent());
        assertEquals(82L,postId);
        assertEquals("운영자임",memberNick);
    }

    @Test
    void getAllComments() {
        //82번 포스트 댓글 조회
        List<CommentResponseDTO> result = commentService.getAllComments(82L);

        result.forEach(commentResponseDTO -> {
            System.out.println(commentResponseDTO);
        });
    }

    @Test
    void validateComment() throws Exception{
        boolean result = commentService.validateComment(101L, "하이트닉");
        boolean result2 = commentService.validateComment(101L, "하이트닉2");

        assertEquals(true,result);
        assertEquals(false,result2);
    }

    @Test
    void deleteComment() throws Exception{
        commentService.deleteComment(101L);

        Optional<Comment> result = commentRepository.findById(101L);

        assertEquals(true,result.isEmpty());
    }

    @Test
    void updateComment() throws Exception{
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setCommentId(115L);
        commentDTO.setContent("수정 테스트");

        commentService.updateComment(commentDTO);


    }

}