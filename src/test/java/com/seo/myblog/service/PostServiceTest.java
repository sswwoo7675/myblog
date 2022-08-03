package com.seo.myblog.service;

import com.seo.myblog.Repository.ContentImgRepository;
import com.seo.myblog.Repository.PostRepository;
import com.seo.myblog.dto.PostDTO;
import com.seo.myblog.dto.PostFormDTO;
import com.seo.myblog.entity.ContentImg;
import com.seo.myblog.entity.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
//@TestPropertySource(locations = "classpath:application-test.properties")
class PostServiceTest {

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @Autowired
    ContentImgRepository contentImgRepository;
    
    /*
    * 테스트용 PostFormDTO 생성
    * */
    PostFormDTO createPostFormDTO(){
        PostFormDTO postFormDTO = new PostFormDTO();
        
        //테스트용 Post 내용 설정
        String testTitle = "testTitle";
        String testContent = "testContent";
        String tags = "가;나/ 다";
        List<String> contentImgs = new ArrayList<>();
        contentImgs.add("/image/content/2022/01/02/aaabb1.png");
        contentImgs.add("/image/content/2022/01/02/aaabb2.png");
        
        postFormDTO.setTitle(testTitle);
        postFormDTO.setContent(testContent);
        postFormDTO.setTags(tags);
        postFormDTO.setContentImgs(contentImgs);


        return postFormDTO;
    }
    

    @Test
    void makeTagString() {
        String testTag = "태그1";
        //String testTag = "태그1, 태그2 ;태그3; 태그4,태그5 ";
        String makeTag;

        makeTag = postService.makeTagString(testTag);

        System.out.println(makeTag);
    }

    @Test
    void addPost() throws Exception{
        PostFormDTO postFormDTO = createPostFormDTO();

        //테스트 대상 메서드 실행
        Long postId = postService.addPost(postFormDTO);

        //저장된 포스트 불러오기
        Post savedPost = postRepository.findById(postId)
                .orElseThrow(EntityNotFoundException::new);
        //저장된 contentImg들 불러오기
        List<ContentImg> savedContentImgs = contentImgRepository
                .findByPostId(savedPost.getId());

        //postFormDTO의 정보와 db에 저장된 정보들이 맞는지 검증
        assertEquals(postFormDTO.getTitle(),savedPost.getTitle());
        assertEquals(postFormDTO.getContent(),savedPost.getContent());
        assertEquals(postFormDTO.getContentImgs().size(),
                savedContentImgs.size());

        for(ContentImg contentImg: savedContentImgs){
            System.out.println(contentImg);
        }


    }


    @Test
    void getPost() throws Exception{
        PostDTO postDTO = postService.getPost(5L);
        System.out.println(postDTO);
    }

    @Test
    void getAllPost() {
        Pageable pageable = PageRequest.of(3,2);

        Page<PostDTO> postDTOPage = postService.getAllPost(pageable);

        System.out.println(postDTOPage.getTotalPages());
        postDTOPage.getContent().forEach(postDTO -> {
            System.out.println(postDTO);
        });
    }

    @Test
    void deletePost() throws Exception{
        postService.deletePost(20L);
    }

    @Test
    void getPostFormDTO() throws Exception{
        System.out.println(postService.getPostFormDTO(5L));
    }
}