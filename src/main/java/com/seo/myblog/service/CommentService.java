package com.seo.myblog.service;

import com.seo.myblog.Repository.CommentRepository;
import com.seo.myblog.Repository.MemberRepository;
import com.seo.myblog.Repository.PostRepository;
import com.seo.myblog.dto.CommentDTO;
import com.seo.myblog.entity.Comment;
import com.seo.myblog.entity.Member;
import com.seo.myblog.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    /*
    *   댓글 쓰기
    * */
    public Long writeComment(CommentDTO commentDTO, String nick) throws Exception {
        //포스트 불러오기
        Post post = postRepository.findById(commentDTO.getPostId()).orElseThrow(EntityNotFoundException::new);
        //Member 불러오기
        Member member = memberRepository.findByNick(nick).orElseThrow(EntityNotFoundException::new);

        Comment comment = new Comment();

        //댓글 내용 셋팅
        comment.setContent(commentDTO.getContent());

        //포스트, 멤버 정보 셋팅
        comment.setMember(member);
        comment.setPost(post);

        //comment db에 저장
        return commentRepository.save(comment).getId();
    }
}













