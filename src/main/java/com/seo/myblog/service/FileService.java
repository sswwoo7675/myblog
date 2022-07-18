package com.seo.myblog.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class FileService {


    /*
    * 파일 저장 함수
    * */
    public String uploadFile(String path, String orgFileName, byte[] fileData) throws IOException {
        UUID uuid = UUID.randomUUID(); //랜덤 uuid 문자열 생성
        String ext = orgFileName.substring(orgFileName.lastIndexOf(".")); //확장자 추출
        String savedFileName = uuid.toString() + ext; //uuid로 새로운 파일명 생성(중복방지)
        String folder = makeFolder(path);

        String fileUploadPath = path + "/" + folder + "/" + savedFileName; //업로드 경로

        FileOutputStream fos = new FileOutputStream(fileUploadPath);
        fos.write(fileData);
        fos.close(); //업로드 작업 수행

        return folder + "/" + savedFileName; //저장된 파일경로 반환
    }


    /*
    * 현재 날짜로 폴더 생성
    * */
    public String makeFolder(String path) {
        String str = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));//현재 날짜 구하기
        String folderPath = str.replace("/", File.separator);

        File file = new File(path, folderPath);

        if (!file.exists()) {  //폴더가 존재하지 않을경우 폴더생성
            file.mkdirs();
        }

        return str;
    }
}
