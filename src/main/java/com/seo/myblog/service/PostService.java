package com.seo.myblog.service;

import com.seo.myblog.Repository.CategoryRepository;
import com.seo.myblog.Repository.PostRepository;
import com.seo.myblog.dto.PostFormDTO;
import com.seo.myblog.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    private final HeadImgService headImgService;

    private final ContentImgService contentImgService;

    private final CategoryRepository categoryRepository;

    private final AttachedFileService attachedFileService;
    
    /*
    * 포스트 작성
    * */
    public Long addPost(PostFormDTO postFormDTO) throws Exception{
        Post post = postFormDTO.createPost();

        //태그문자열 변환
        String oldTag = post.getTags();
        String newTag = makeTagString(oldTag);
        post.setTags(newTag);

        //카테고리 정보가 존재하면
        if(postFormDTO.getCategoryId() != null){
            //카테고리 엔티티 얻어와서 Post에 셋팅
            Long categoryId = postFormDTO.getCategoryId();
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(EntityNotFoundException::new);
            post.setCategory(category);
        }
        
        //포스트 저장
        Post savedPost = postRepository.save(post);

        //본문 이미지가 존재할 경우 그 정보저장
        if(!postFormDTO.getContentImgs().isEmpty()){
            for(String contentImgUrl: postFormDTO.getContentImgs()){
                ContentImg contentImg = new ContentImg();
                contentImg.setPost(savedPost);
                contentImgService.saveContentImg(contentImgUrl, contentImg);
            }
        }
        
        //헤드 이미지가 존재할 경우 업로드 및 정보 저장
        if((postFormDTO.getHeadImgFile() != null)
                && (!postFormDTO.getHeadImgFile().isEmpty())){
            MultipartFile headImgFile = postFormDTO.getHeadImgFile();
            HeadImg headImg = new HeadImg();
            headImg.setPost(savedPost);
            headImgService.saveHeadImg(headImgFile,headImg);
        }
        
        //첨부파일이 존재할 경우 첨부파일 업로드 및 정보 저장
        if((postFormDTO.getUploadFile() != null)
                && (!postFormDTO.getUploadFile().isEmpty())){
            MultipartFile uploadFile = postFormDTO.getUploadFile();
            AttachedFile attachedFile = new AttachedFile();
            attachedFile.setPost(savedPost);
            attachedFileService.saveAttachedFile(uploadFile, attachedFile);
        }

        return savedPost.getId();
    }

    /*
     * 형식에 맞는 태그문자열로 변환하는 메서드(태그1/태그2/태그3)
     * */
    public String makeTagString(String tags) {
        //태그구분문자 모두 ;로 통일
        tags = tags.replace(",", ";");
        String[] tagList = tags.split(";");

        String makeTags;

        //각 태그들의 앞뒤 공백 제거
        for(int i = 0; i<tagList.length; i++){
            tagList[i] = tagList[i].strip();
        }

        //구분자를 / 로하여 하나의 문자열로 변환
        makeTags = String.join("/", tagList);

        return makeTags;
    }
}
