package com.seo.myblog.service;

import com.seo.myblog.Repository.CategoryRepository;
import com.seo.myblog.dto.CategoryDTO;
import com.seo.myblog.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;


    /*
    * 모든 카테고리 정보 조회 : 카테고리 내 포스트 수 포함
    * */
    @Transactional(readOnly = true)
    public List<CategoryDTO> getAllCategoriesInfo(){
        List<Object[]> results = categoryRepository.findCategoryInfo();

        List<CategoryDTO> categoryDTOList = results.stream().map(objects -> {
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setId((Long) objects[0]); //category id
            categoryDTO.setName((String) objects[1]);//category 이름
            categoryDTO.setCountPost((Long) objects[2]);//category내 포스트 개수
            return categoryDTO;
        }).collect(Collectors.toList());

        return categoryDTOList;
    }
}










