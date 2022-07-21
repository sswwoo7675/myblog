package com.seo.myblog.service;

import com.seo.myblog.Repository.AttachedFileRepository;
import com.seo.myblog.Repository.PostRepository;
import com.seo.myblog.entity.AttachedFile;
import com.seo.myblog.entity.Post;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.parameters.P;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class AttachedFileServiceTest {

    @Autowired
    AttachedFileService attachedFileService;

    @Autowired
    AttachedFileRepository attachedFileRepository;

    @Autowired
    PostRepository postRepository;

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
    void saveAttachedFile() throws Exception{
        Post post = savePost(); //테스트용 포스트 생성

        String path = "C:/test/test/";
        String fileName = "testMockFile.exe";
        MockMultipartFile multipartFile = new MockMultipartFile(path,fileName,"image/jpg", new byte[]{1,2,3,4}); //테스트용 multipartFile 생성

        AttachedFile attachedFile = new AttachedFile();
        attachedFile.setPost(post); //attachedFile 생성후 post setting

        Long attachedFileId = attachedFileService.saveAttachedFile(multipartFile,attachedFile); //attachedFile 저장

        AttachedFile savedAttachedFile = attachedFileRepository.findById(attachedFileId).orElseThrow(EntityNotFoundException::new); //저장된 attachedFile 가져오기

        Assertions.assertEquals(fileName, savedAttachedFile.getOrgFileName()); //file이름과 db에 저장된 정보가 일치하는지 확인
        System.out.println(savedAttachedFile);
    }
}