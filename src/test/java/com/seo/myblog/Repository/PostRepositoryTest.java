package com.seo.myblog.Repository;

import com.seo.myblog.entity.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Test
    void findPostsByContentAndTitle() {
        //pageable 생성: 페이지 값이 없을경우 0(첫페이지), 있을경우 페이지 값 -1, size(페이지 당 자료개수)는 5개로 설정
        Pageable pageable = PageRequest.of(0,20);
        Page<Post> postList = postRepository.findPostsByContentAndTitle("프로그래밍",pageable);

        for(Post post : postList.getContent()){
            System.out.println(post);
        }
    }

    @Test
    void findByCategoryIdOrderByRegTimeDesc() {
        //pageable 생성: 페이지 값이 없을경우 0(첫페이지), 있을경우 페이지 값 -1, size(페이지 당 자료개수)는 5개로 설정
        Pageable pageable = PageRequest.of(0,20);
        Page<Post> postList = postRepository.findByCategoryIdOrderByRegTimeDesc(3L,pageable);

        for(Post post : postList.getContent()){
            System.out.println(post);
        }
    }

    @Test
    void findByTagsContainingOrderByRegTimeDesc() {
        //pageable 생성: 페이지 값이 없을경우 0(첫페이지), 있을경우 페이지 값 -1, size(페이지 당 자료개수)는 5개로 설정
        Pageable pageable = PageRequest.of(0,20);
        Page<Post> postList = postRepository.findByTagsContainingOrderByRegTimeDesc("테스트",pageable);

        for(Post post : postList.getContent()){
            System.out.println(post);
        }
    }

    @Test
    void findPostsWithHeadImg() {
        //pageable 생성: 페이지 값이 없을경우 0(첫페이지), 있을경우 페이지 값 -1, size(페이지 당 자료개수)는 5개로 설정
        Pageable pageable = PageRequest.of(0,30);
        Page<Object[]> objList = postRepository.findPostsWithHeadImg(pageable);

        for(Object[] obj: objList.getContent()){
            System.out.println(obj[0]);
            System.out.println(obj[1]);
        }
    }
}