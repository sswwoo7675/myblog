package com.seo.myblog.service;

import com.seo.myblog.Repository.CategoryRepository;
import com.seo.myblog.dto.CategoryDTO;
import com.seo.myblog.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class CategoryServiceTest {

    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryRepository categoryRepository;

    /*
    * 테스트용 카테고리 저장
    * */
    void saveCategory(){
        Category category1 = new Category();
        Category category2 = new Category();
        Category category3 = new Category();

        category1.setName("카테고리1");
        category2.setName("카테고리2");
        category3.setName("카테고리3");

        categoryRepository.save(category1);
        categoryRepository.save(category2);
        categoryRepository.save(category3);
    }


}