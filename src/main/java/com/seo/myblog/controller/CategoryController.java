package com.seo.myblog.controller;

import com.seo.myblog.dto.CategoryDTO;
import com.seo.myblog.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    /*
    * 모든 카테고리 정보 제공 rest api
    * */
    @GetMapping("/category/info")
    public @ResponseBody ResponseEntity categoryInfo(){
        List<CategoryDTO> categoryDTOList = categoryService.getAllCategoriesInfo();

        return new ResponseEntity<List<CategoryDTO>>(categoryDTOList, HttpStatus.OK);
    }
}
