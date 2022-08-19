package com.seo.myblog.service;

import com.seo.myblog.Repository.CommentRepository;
import com.seo.myblog.Repository.MemberRepository;
import com.seo.myblog.Repository.PostRepository;
import com.seo.myblog.dto.CommentDTO;
import com.seo.myblog.dto.CommentResponseDTO;
import com.seo.myblog.entity.Comment;
import com.seo.myblog.entity.Member;
import com.seo.myblog.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

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

        //댓글 내용 셋팅(\n => <br />로 변경)
        String newContent = commentDTO.getContent().replace("\n","<br/>");
        comment.setContent(newContent);

        //포스트, 멤버 정보 셋팅
        comment.setMember(member);
        comment.setPost(post);

        //comment db에 저장
        return commentRepository.save(comment).getId();
    }

    /*
    * 댓글 리스트 보기
    * */
    @Transactional(readOnly = true)
    public List<CommentResponseDTO> getAllComments(Long postId){
        //해당 포스트의 모든 comment 조회
        List<Comment> commentList= commentRepository.findByPostIdOrderByRegTimeAsc(postId);

        //List<CommentResponseDTO>형태로 변경
        List<CommentResponseDTO> commentResponseDTOList = commentList.stream().map(comment -> {
            CommentResponseDTO commentResponseDTO = new CommentResponseDTO();
            commentResponseDTO.setCommentId(comment.getId());
            commentResponseDTO.setContent(comment.getContent());
            commentResponseDTO.setCommentDate(comment.getRegTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
            commentResponseDTO.setMemberAvatar(comment.getMember().getAvatar());
            commentResponseDTO.setMemberNick(comment.getMember().getNick());

            return commentResponseDTO;
        }).collect(Collectors.toList());

        return commentResponseDTOList;
    }

    /*
    * 댓글 작성 사용자 검증(댓글 쓴 사람과 현재 로그인한 사용자의 닉네임 비교)
    * */
    @Transactional(readOnly = true)
    public boolean validateComment(Long commentId, String nick) throws Exception{
        //해당 댓글 조회
        Comment comment = commentRepository.findById(commentId).orElseThrow(EntityNotFoundException::new);

        //댓글 쓴 멤버 닉네임 조회
        String writer = comment.getMember().getNick();

        //닉네임 비교
        if(writer.equals(nick)){
            return true;
        } else {
            return false;
        }
    }

    /*
    * 댓글삭제
    * */
    public void deleteComment(Long commentId) throws Exception {
        commentRepository.deleteById(commentId);
    }
}













