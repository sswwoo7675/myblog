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
import javax.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@Transactional
//@TestPropertySource(locations = "classpath:application-test.properties")
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

    @Test
    void updateContentImg() throws Exception{
        Post post = postRepository.findById(61L).orElseThrow(EntityNotFoundException::new);
        List<ContentImg> contentImgList = contentImgRepository.findByPostId(61L);

        List<String> newUrls = new ArrayList<>();

        newUrls.add("/images/content/2022/08/02/dfsfdsdfs1.jpg");
        newUrls.add("/images/content/2022/08/02/dfsfdfds2.jpg");

        contentImgService.updateContentImg(newUrls,contentImgList,post);
    }

    @Test
    void compareNewToOld() {
        List<ContentImg> contentImgList = contentImgRepository.findByPostId(61L);

        boolean isNew = contentImgService.compareNewToOld("/images/content/2022/08/03/5f863eca-9ad7-4e05-9cb2-524c38621096.jpg",contentImgList);
        System.out.println(isNew);
    }

    @Test
    void compareOldToNew() throws Exception{
        List<String> newUrls = new ArrayList<>();
        ContentImg contentImg = contentImgRepository.findById(54L).orElseThrow(EntityNotFoundException::new);

        newUrls.add("/images/content/2022/08/02/0cf9ee2c-1e2c-42f3-bcee-f7a7309470d3.jpg");
        newUrls.add("dfffdfd");
        newUrls.add("fvdfvdfvfdvfdvdfvfd");
        boolean isNew = contentImgService.compareOldToNew(contentImg.getImgUrl(), newUrls);
        System.out.println(isNew);
    }
}










