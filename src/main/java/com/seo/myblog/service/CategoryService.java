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
    * 모든 카테고리 정보 조회
    * */
    @Transactional(readOnly = true)
    public List<CategoryDTO> getAllCategories(){
        //모든 카테고리 Entity 조회
        List<Category> categoryList = categoryRepository.findAll();

        //Entity 리스트를 DTO 리스트로 변경
        List<CategoryDTO> categoryDTOList = categoryList.stream()
                .map(category -> CategoryDTO.of(category))
                .collect(Collectors.toList());

        return categoryDTOList;
    }
}










