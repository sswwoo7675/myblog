package com.seo.myblog.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SearchInfoDTO {
    private String type; //검색타입
    private String searchWord; //검색어
}
