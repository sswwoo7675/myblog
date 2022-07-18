package com.seo.myblog.controller;

import com.seo.myblog.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ContentImgUploadController {

    @Value("${contentImgLocation}")
    String contentImgLocation; //c:/myblog/content

    private final FileService fileService;

    @PostMapping(value = "/ajax/uploadContentImg")
    public @ResponseBody ResponseEntity uploadImg(@RequestParam("upload") MultipartFile multipartFile) throws IOException {
        String orgFileName = multipartFile.getOriginalFilename(); //파일이름 추출(확장자 포함)
        byte[] fileData = multipartFile.getBytes(); //파일데이터 추출
        String uploadFilePath = fileService.uploadFile(contentImgLocation,orgFileName,fileData); //업로드된 파일경로 반환


        Map returnData = new HashMap<>();
        returnData.put("uploaded",true);
        returnData.put("url","/images/content/" + uploadFilePath); //이미지 업로드 정보 생성

        return new ResponseEntity<Map>(returnData, HttpStatus.OK); //이미지 업로드 정보 반환
    }
}
