package com.seo.myblog.service;

import com.seo.myblog.Repository.HeadImgRepository;
import com.seo.myblog.entity.HeadImg;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class HeadImgService {

    @Value("${headImgLocation}")
    private String headImgLocation;

    private final FileService fileService;

    private final HeadImgRepository headImgRepository;

    /*
    * 포스트 헤드 이미지 업로드 및 db에 저장 메서드
    * */
    public Long saveHeadImg(MultipartFile multipartFile, HeadImg headImg) throws Exception{
        String orgImgName = multipartFile.getOriginalFilename(); //이미지 파일명 가져오기
        String imgUrl = fileService.uploadFile(headImgLocation, orgImgName,multipartFile.getBytes()); //이미지 저장후 저장경로 가져옴
        imgUrl = "/myblog/headimg/" + imgUrl; //이미지 풀 경로
        String imgName = imgUrl.substring(imgUrl.lastIndexOf('/')+1); //풀 경로로부터 파일이름만 추출

        headImg.setImgName(imgName);
        headImg.setImgUrl(imgUrl);
        headImg.setOrgImgName(orgImgName);

        return headImgRepository.save(headImg).getId();

    }
}












