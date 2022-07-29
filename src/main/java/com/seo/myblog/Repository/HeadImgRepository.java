package com.seo.myblog.Repository;

import com.seo.myblog.entity.HeadImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HeadImgRepository extends JpaRepository<HeadImg,Long> {

    Optional<HeadImg> findByPostId(Long postId);
}
