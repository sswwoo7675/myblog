package com.seo.myblog.Repository;

import com.seo.myblog.entity.AttachedFile;
import com.seo.myblog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttachedFileRepository extends JpaRepository<AttachedFile,Long> {
    Optional<AttachedFile> findByPostId(Long postId);
}
