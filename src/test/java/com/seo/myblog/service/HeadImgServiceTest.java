package com.seo.myblog.service;

import com.seo.myblog.Repository.HeadImgRepository;
import com.seo.myblog.Repository.PostRepository;
import com.seo.myblog.entity.HeadImg;
import com.seo.myblog.entity.Post;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class HeadImgServiceTest {

    @Autowired
    HeadImgService headImgService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    HeadImgRepository headImgRepository;

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
    void saveHeadImg() throws Exception{
        Post post = savePost(); //테스트용 임시 POST생성

        String path = "C:/test/test/";
        String imgName = "testMockFile.exe";
        MockMultipartFile multipartFile = new MockMultipartFile
                (path,imgName,"image/jpg", new byte[]{1,2,3,4});
        //테스트용 multipartFile 생성

        //headImg 생성 후 POST 셋팅
        HeadImg headImg = new HeadImg();
        headImg.setPost(post);

        //테스트 대상 함수 실행
        Long headImgId = headImgService.saveHeadImg(multipartFile,headImg);

        //테스트 검증을 위해 저장된 HeadImg 가져옴
        HeadImg savedHeadImg = headImgRepository.findById(headImgId)
                .orElseThrow(EntityNotFoundException::new);

        //imgName과 db에 저장된 정보가 일치하는지 검증
        Assertions.assertEquals(imgName, savedHeadImg.getOrgImgName());
        System.out.println(savedHeadImg);


    }
}










