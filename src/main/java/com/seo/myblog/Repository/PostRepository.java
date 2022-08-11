package com.seo.myblog.Repository;

import com.seo.myblog.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {

    @Query("select p " +
            "from Post p " +
            "order by p.regTime desc")
    Page<Post> findPosts(Pageable pageable); //모든 포스트 조회(내림차순 정렬)

    @Query("select p, h " +
            "from Post p left join HeadImg h " +
            "on h.post = p " +
            "order by p.regTime desc")
    Page<Object[]> findPostsWithHeadImg(Pageable pageable); //모든 포스트 조회(Join)

    @Query("select p " +
            "from Post p " +
            "where p.title like %:searchWord% " +
            "or p.content like %:searchWord% " +
            "order by p.regTime desc")
    Page<Post> findPostsByContentAndTitle(@Param("searchWord") String searchWord, Pageable pageable); //제목+내용으로 포스트 조회

    Page<Post> findByCategoryIdOrderByRegTimeDesc(Long categoryId, Pageable pageable); //카테고리id로 포스트 조회

    Page<Post> findByTagsContainingOrderByRegTimeDesc(String tag, Pageable pageable); //태그로 검색하기


}
