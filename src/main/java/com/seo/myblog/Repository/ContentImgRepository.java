package com.seo.myblog.Repository;

import com.seo.myblog.entity.ContentImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContentImgRepository extends JpaRepository<ContentImg, Long> {
    Optional<List<ContentImg>> findByPostId(Long postId);
}
