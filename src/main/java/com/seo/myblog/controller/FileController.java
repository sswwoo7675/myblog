package com.seo.myblog.controller;

import com.seo.myblog.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class FileController {

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

    /*
    * 파일 다운로드 함수
    * */
    @GetMapping(value = "/ajax/fileDownload")
    public @ResponseBody ResponseEntity<Object> download(@RequestHeader("User-Agent") String agent, String url, String orgFileName){
        String path = "C:/myblog" + url;

        try {
            Path filePath = Paths.get(path);
            Resource resource = new InputStreamResource(Files.newInputStream(filePath)); //파일 리소스 얻기

            File file = new File(path);

            HttpHeaders headers = new HttpHeaders();
            
            String fileName = transFileName(agent,orgFileName); //한글 파일 처리를 위한 인코딩
            
            headers.setContentDisposition(ContentDisposition.builder("attachment").filename(fileName).build());

            return new ResponseEntity<Object>(resource, headers, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
        }
    }

    /*
    * 한글 파일명 처리 관련 인코딩 함수
    * */
    public String transFileName(String agent, String orgFileName) throws Exception{

        String fileName;

        if(agent.contains("Trident"))//Internet Explore
            fileName = URLEncoder.encode(orgFileName, "UTF-8").replaceAll("\\+", " ");

        else if(agent.contains("Edge")) //Micro Edge
            fileName = URLEncoder.encode(orgFileName, "UTF-8");

        else //Chrome
            fileName = new String(orgFileName.getBytes("UTF-8"), "ISO-8859-1");

        return fileName;

    }
}
