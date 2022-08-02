package com.seo.myblog.Repository;

import com.seo.myblog.entity.ContentImg;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ContentImgRepositoryTest {

    @Autowired
    ContentImgRepository contentImgRepository;

    @Test
    void findByPostId() {
        List<ContentImg> contentImgList = contentImgRepository.findByPostId(16L);
        System.out.println(contentImgList.isEmpty());
    }
}