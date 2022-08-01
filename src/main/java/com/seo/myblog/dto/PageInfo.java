package com.seo.myblog.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/*
* 페이징 관련 정보 저장 DTO
* */
@Getter @Setter
public class PageInfo {
    private int totalPage; //총 페이지수

    private int page; //현재 페이지

    private int size; //페이지 당 자료개수

    private int start, end; //페이징 시작, 끝 번호

    private boolean prev, next; //prev, next 버튼 여부

    private List<Integer> pageList; //페이징 번호

    public PageInfo(Page page){
        totalPage = page.getTotalPages();
    }

    private void makePageList(Pageable pageable){
        this.page = pageable.getPageNumber() + 1 ;//0부터 페이지가 시작하므로 +1
        this.size = pageable.getPageSize(); //페이지 당 자료개수

        //페이지 번호 5개씩 나열 ( prev 1 2 3 4 5 next)
        int tmpEnd = (int)(Math.ceil(page/5.0) * 5); //임시 마지막 페이지 구하기 5,10,15,20... 

        start = tmpEnd - 4; //첫페이지 구함
        prev = start > 1; //첫페이지가 1보다 클 경우 prev버튼 보여야 함
        end = totalPage > tmpEnd ? tmpEnd : totalPage; //진짜 마지막 페이지 구하기(totalpage가 더 큰경우는 tmpEnd사용, 아니면 totalPage로 변경)
        next = totalPage > end; //totalpage가 end보다 더 클경우 아직 뒷 페이지가 있다는 소리이므로 next버튼 보여야함

        pageList = IntStream.rangeClosed(start,end).boxed().collect(Collectors.toList()); //페이징번호 구하기
    }
}
