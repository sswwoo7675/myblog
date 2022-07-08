package com.seo.myblog.controller;

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
public class ContentImgUploadController {

    @Value("${contentImgLocation}")
    String contentImgLocation; //c:/myblog/content


    @PostMapping(value = "/ajax/uploadContentImg")
    public @ResponseBody ResponseEntity uploadImg(@RequestParam("upload") MultipartFile multipartFile) throws IOException {
        String orgFileName = multipartFile.getOriginalFilename();
        byte[] fileData = multipartFile.getBytes();
        UUID uuid = UUID.randomUUID();
        String ext = orgFileName.substring(orgFileName.lastIndexOf(".")); //확장자만 추출

        String savedFileName = uuid.toString() + ext; //uuid사용한 fileName
        String fileUploadFullUrl = contentImgLocation + "/" + savedFileName; //파일 업로드 할 풀 경로

        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
        fos.write(fileData); //지정 경로에 파일쓰기
        fos.close();

        Map returnData = new HashMap<>();
        returnData.put("uploaded",true);
        returnData.put("url","/images/content/" + savedFileName); //이미지 업로드 정보 생성

        return new ResponseEntity<Map>(returnData, HttpStatus.OK); //이미지 업로드 정보 반환
    }
}
