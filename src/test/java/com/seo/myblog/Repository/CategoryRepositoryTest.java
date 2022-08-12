package com.seo.myblog.Repository;

import com.seo.myblog.dto.CategoryDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryRepositoryTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    void findCategoryInfo() {
        List<Object[]> results = categoryRepository.findCategoryInfo();

        List<CategoryDTO> categoryDTOList = results.stream().map(objects -> {
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setId((Long) objects[0]);
            categoryDTO.setName((String) objects[1]);
            categoryDTO.setCountPost((Long) objects[2]);
            return categoryDTO;
        }).collect(Collectors.toList());

        categoryDTOList.forEach(categoryDTO -> {
            System.out.println(categoryDTO);
        });
    }
}