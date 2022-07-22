package com.seo.myblog.service;

import com.seo.myblog.Repository.ContentImgRepository;
import com.seo.myblog.Repository.PostRepository;
import com.seo.myblog.entity.ContentImg;
import com.seo.myblog.entity.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class ContentImgServiceTest {

    @Autowired
    ContentImgService contentImgService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    ContentImgRepository contentImgRepository;

    /*
     * 테스트용 Post 생성
     * */
    Post savePost(){
        Post post = new Post();

        post.setTitle("hi");
        post.setContent("test");

        return postRepository.save(post);
    }

    @Test
    void saveContentImg() {
        //테스트용 Post 생성
        Post post = savePost();
        //테스트용 imgUrl 생성
        String contentImgUrl = "/image/content/2022/01/02/aaabb.png";
        ContentImg contentImg = new ContentImg();
        contentImg.setPost(post);

        //테스트 대상메서드 실행
        Long contentImgId = contentImgService
                .saveContentImg(contentImgUrl,contentImg);

        //검증을 위해 저장된 contentImg 불러옴
        ContentImg savedContentImg = contentImgRepository.findById(contentImgId)
                .orElseThrow(EntityExistsException::new);

        //테스트용 url과 실제 저장된 url 일치여부 검증
        assertEquals(contentImgUrl, savedContentImg.getImgUrl());

        System.out.println(contentImg);
    }
}










