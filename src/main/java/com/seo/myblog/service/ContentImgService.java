package com.seo.myblog.service;

import com.seo.myblog.Repository.ContentImgRepository;
import com.seo.myblog.entity.ContentImg;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ContentImgService {

    @Value("${contentImgLocation}")
    private String contentImgLocation;
    private final ContentImgRepository contentImgRepository;

    private final FileService fileService;



    /*
    * contentImg 저장
    * */
    public Long saveContentImg(String contentImgUrl, ContentImg contentImg){
        String contentImgName = contentImgUrl.substring(contentImgUrl.lastIndexOf("/") + 1);
        contentImg.setImgUrl(contentImgUrl);
        contentImg.setImgName(contentImgName);

        return contentImgRepository.save(contentImg).getId();
    }

    /*
    * contentImg 삭제
    * */
    public void deleteContentImg(ContentImg contentImg) throws Exception{

        //파일 저장 경로 불러와서 파일 삭제하기
        String path =  contentImgLocation + contentImg.getImgUrl().split("content")[1];
        fileService.deleteFile(path);
        
        //db에서 정보 삭제
        contentImgRepository.delete(contentImg);
    }
}
