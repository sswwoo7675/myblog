package com.seo.myblog.service;

import com.seo.myblog.Repository.*;
import com.seo.myblog.dto.CategoryDTO;
import com.seo.myblog.dto.PostDTO;
import com.seo.myblog.dto.PostFormDTO;
import com.seo.myblog.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.swing.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    private final HeadImgService headImgService;

    private final HeadImgRepository headImgRepository;

    private final ContentImgService contentImgService;

    private final ContentImgRepository contentImgRepository;

    private final CategoryRepository categoryRepository;

    private final AttachedFileService attachedFileService;

    private final AttachedFileRepository attachedFileRepository;
    
    /*
    * 포스트 저장
    * */
    public Long addPost(PostFormDTO postFormDTO) throws Exception{
        Post post = postFormDTO.createPost();

        //태그 정보 존재하면 태그문자열 변환
        String oldTag = post.getTags();
        if(oldTag != null && !oldTag.isBlank()){
            String newTag = makeTagString(oldTag);
            post.setTags(newTag);
        }

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
    * 포스트 정보 불러오기
    * */
    @Transactional(readOnly = true)
    public PostDTO getPost(Long postId) throws Exception{
        Post post = postRepository.findById(postId).orElseThrow(EntityNotFoundException::new); //포스트 Entity 불러오기
        PostDTO postDTO = new PostDTO();

        Category category = post.getCategory();  //포스트의 카테고리 조회
        CategoryDTO categoryDTO;
        if(category != null){ //카테고리가 존재하면 카테고리 정보 postDTO에 저장
            categoryDTO = CategoryDTO.of(category);
            postDTO.setCategoryDTO(categoryDTO);
        }

        //postId로 headImg와 AttachedFile DB에서 불러오기
        Optional<HeadImg> optionalHeadImg = headImgRepository.findByPostId(postId);
        Optional<AttachedFile> optionalAttachedFile = attachedFileRepository.findByPostId(postId);

        //headImg가 존재하면 postDTO에 url 정보 저장
        if(optionalHeadImg.isPresent()){
            HeadImg headImg = optionalHeadImg.get();
            postDTO.setHeadImgUrl(headImg.getImgUrl());
        }

        //attachedFile이 존재하면 postDTO에 url과 파일이름 정보 저장
        if(optionalAttachedFile.isPresent()){
            AttachedFile attachedFile = optionalAttachedFile.get();
            postDTO.setAttachedFileUrl(attachedFile.getFileUrl());
            postDTO.setAttachedFileName(attachedFile.getOrgFileName());
        }

        //post Entity 내용을 postDTO에 저장
        postDTO.setPostId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setHook_text(post.getHook_text());
        postDTO.setContent(post.getContent());
        postDTO.setWriter(post.getCreatedBy());

        //태그가 존재할 경우 태그 각각을 리스트 형태로 저장함
        if(post.getTags() != null && !post.getTags().isBlank()){
            postDTO.setTags(Arrays.asList(post.getTags().split("/")));
        }
        postDTO.setPostDate(post.getRegTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

        return postDTO;

    }

    /*
    * 모든 포스트 정보 조회
    * */
    @Transactional(readOnly = true)
    public Page<PostDTO> getAllPost(Pageable pageable){
        List<Post> postList = postRepository.findPosts(pageable); //모든 post조회
        Long count = postRepository.count(); //엔티티 총 개수
        
        //Entity List를 DTO List로 변환
        List<PostDTO> postDTOList= postList.stream().map(post -> {
            PostDTO postDTO = new PostDTO();

            Category category = post.getCategory();  //포스트의 카테고리 조회
            CategoryDTO categoryDTO;
            if(category != null){ //카테고리가 존재하면 카테고리 정보 postDTO에 저장
                categoryDTO = CategoryDTO.of(category);
                postDTO.setCategoryDTO(categoryDTO);
            }

            //postId로 headImg DB에서 불러오기
            Optional<HeadImg> optionalHeadImg = headImgRepository.findByPostId(post.getId());
            //headImg가 존재하면 postDTO에 url 정보 저장
            if(optionalHeadImg.isPresent()){
                HeadImg headImg = optionalHeadImg.get();
                postDTO.setHeadImgUrl(headImg.getImgUrl());
            }

            postDTO.setPostId(post.getId());
            postDTO.setTitle(post.getTitle());
            postDTO.setHook_text(post.getHook_text());
            postDTO.setWriter(post.getCreatedBy());
            postDTO.setPostDate(post.getRegTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
            //태그가 존재할 경우 태그 각각을 리스트 형태로 저장함
            if(post.getTags() != null && !post.getTags().isBlank()){
                postDTO.setTags(Arrays.asList(post.getTags().split("/")));
            }

            return postDTO;
        }).collect(Collectors.toList());

        return new PageImpl<PostDTO>(postDTOList,pageable,count);
    }

    /*
    * 포스트 제거
    * */
    public void deletePost(Long postId) throws Exception {
        //postId 이용하여 ContentImg, HeadImg, AttachedFile 불러옴
        List<ContentImg> contentImgList = contentImgRepository.findByPostId(postId);
        Optional<HeadImg> optHeadImg = headImgRepository.findByPostId(postId);
        Optional<AttachedFile> optAttachedFile = attachedFileRepository.findByPostId(postId);

        //ContentImg가 존재할 경우 삭제
        if(!contentImgList.isEmpty()){
            for(ContentImg contentImg:contentImgList){
                contentImgService.deleteContentImg(contentImg);
            }
        }
        
        //HeadImg가 존재할 경우 삭제
        if(optHeadImg.isPresent()){
            headImgService.deleteHeadImg(optHeadImg.get());
        }
        
        //AttachedFile이 존재할 경우 삭제
        if(optAttachedFile.isPresent()){
            attachedFileService.deleteAttachedFile(optAttachedFile.get());
        }

        //post 제거
        Post post = postRepository.findById(postId).orElseThrow(EntityNotFoundException::new);
        postRepository.delete(post);
    }

    /*
    * 포스트 수정 기능시 필요한 PostFormDTO 생성
    * */
    public PostFormDTO getPostFormDTO(Long postId) throws Exception{
        //postId로 post불러오기
        Post post = postRepository.findById(postId).orElseThrow(EntityNotFoundException::new);

        //postFormDTO로 변환
        PostFormDTO postFormDTO = PostFormDTO.of(post);
        
        //headImg와 AttachedFile 불러오기
        Optional<HeadImg> optHeadImg = headImgRepository.findByPostId(post.getId());
        Optional<AttachedFile> optAttachedFile = attachedFileRepository.findByPostId(post.getId());

        //headImg와 AttachedFile이 존재한다면 postFormDTO에 정보 저장
        if(optHeadImg.isPresent()){
            HeadImg headImg = optHeadImg.get();
            postFormDTO.setHeadImgFileName(headImg.getOrgImgName());
        }

        if(optAttachedFile.isPresent()){
            AttachedFile attachedFile = optAttachedFile.get();
            postFormDTO.setUploadFileName(attachedFile.getOrgFileName());
        }

        return postFormDTO;
    }

    /*
    * 포스트 작성자 검증
    * */
    @Transactional(readOnly = true)
    public boolean validatePost(Long postId, String Nick) throws Exception{
        Post post = postRepository.findById(postId).orElseThrow(EntityNotFoundException::new);

        //포스트 작성자랑 로그인 사용자랑 다르다면 false
        if(!post.getCreatedBy().equals(Nick)){
            return false;
        } else {
            return true;
        }
    }

    /*
     * 형식에 맞는 태그문자열로 변환하는 메서드(태그1/태그2/태그3)
     * */
    public String makeTagString(String tags) {
        //태그구분문자 모두 /로 통일
        tags = tags.replace(",", "/");
        tags = tags.replace(";", "/");
        String[] tagList = tags.split("/");

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
