package com.seo.myblog.Repository;

import com.seo.myblog.entity.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {

    @Query("select p " +
            "from Post p " +
            "order by p.regTime desc")
    List<Post> findPosts(Pageable pageable); //모든 포스트 조회(내림차순 정렬)
}
