package com.seo.myblog.service;


import com.seo.myblog.Repository.AttachedFileRepository;
import com.seo.myblog.entity.AttachedFile;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class AttachedFileService {

    @Value("${attachedFileLocation}")
    private String attachedFileLocation;

    private final FileService fileService;

    private final AttachedFileRepository attachedFileRepository;

    /*
    * 첨부파일 업로드 및 db에 저장 메서드
    * */
    public Long saveAttachedFile(MultipartFile multipartFile, AttachedFile attachedFile) throws Exception{
        String orgFileName = multipartFile.getOriginalFilename(); //파일명 가져오기
        String fileUrl = fileService.uploadFile(attachedFileLocation,orgFileName, multipartFile.getBytes()); //파일 업로드 수행
        fileUrl = "/attached/" + fileUrl; //저장된 파일 full경로
        String fileName = fileUrl.substring(fileUrl.lastIndexOf('/') + 1); //full경로로 부터 파일이름만 추출

        attachedFile.setFileName(fileName);
        attachedFile.setOrgFileName(orgFileName);
        attachedFile.setFileUrl(fileUrl);

        return attachedFileRepository.save(attachedFile).getId();
    }

    /*
    * attachedFile 삭제
    * */
    public void deleteAttachedFile(AttachedFile attachedFile) throws Exception{
        //파일 저장 경로 불러와서 파일 삭제하기
        String path = attachedFileLocation + attachedFile.getFileUrl().split("attached")[1];
        fileService.deleteFile(path);

        //db에서 정보 삭제
        attachedFileRepository.delete(attachedFile);
    }
}
