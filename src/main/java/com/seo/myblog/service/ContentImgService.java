package com.seo.myblog.service;

import com.seo.myblog.Repository.ContentImgRepository;
import com.seo.myblog.entity.ContentImg;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ContentImgService {

    private final ContentImgRepository contentImgRepository;

    /*
    * contentImg 저장
    * */
    public Long saveContentImg(String contentImgUrl, ContentImg contentImg){
        String contentImgName = contentImgUrl.substring(contentImgUrl.lastIndexOf("/") + 1);
        contentImg.setImgUrl(contentImgUrl);
        contentImg.setImgName(contentImgName);

        return contentImgRepository.save(contentImg).getId();
    }
}
