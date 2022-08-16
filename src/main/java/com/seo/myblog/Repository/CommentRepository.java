package com.seo.myblog.Repository;

import com.seo.myblog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    //해당 포스트의 모든 comment 조회하여 RegTime 오름차순 정렬
    public List<Comment> findByPostIdOrderByRegTimeAsc(Long postId);
}
