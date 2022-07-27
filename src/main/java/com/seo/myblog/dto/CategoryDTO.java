package com.seo.myblog.dto;

import com.seo.myblog.entity.Category;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

/*
* 카테고리 정보
* */
@Getter
@Setter
@ToString
public class CategoryDTO {
    private Long id; //카테고리 id

    private String name; //카테고리 이름

    private static ModelMapper modelMapper = new ModelMapper();

    //Entity -> DTO
    public static CategoryDTO of(Category category){
        return modelMapper.map(category, CategoryDTO.class);
    }
}
