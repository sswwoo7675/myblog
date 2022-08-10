package com.seo.myblog.service;

import com.seo.myblog.Repository.ContentImgRepository;
import com.seo.myblog.entity.ContentImg;
import com.seo.myblog.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
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
        //외부이미지의 경우 저장x
        if(contentImgUrl.contains("http://") || contentImgUrl.contains("https://")){
            return null;
        }

        String contentImgName = contentImgUrl.substring(contentImgUrl.lastIndexOf("/") + 1);
        contentImg.setImgUrl(contentImgUrl);
        contentImg.setImgName(contentImgName);

        return contentImgRepository.save(contentImg).getId();
    }

    /*
    * contentImg 업데이트
    * */
    public void updateContentImg(List<String> newContentImgUrls, List<ContentImg> contentImgList, Post post) throws Exception{

        //기존에 있는 Url이 newContentImgUrl에 없으면 기존 contentImg제거
        List<Integer> removeTargetIdx = new ArrayList<>();
        for(int i=0; i<contentImgList.size(); i++){
            String oldUrl = contentImgList.get(i).getImgUrl();
            if(!compareOldToNew(oldUrl,newContentImgUrls)){
                removeTargetIdx.add(i);
            }
        }

        for(int idx: removeTargetIdx){
            ContentImg target = contentImgList.get(idx);
            deleteContentImg(target);
        }

        //새로운 url이 기존 contentImg들에 없으면 정보 추가
        for(String newUrl : newContentImgUrls){
            if(!compareNewToOld(newUrl,contentImgList)){
                ContentImg contentImg = new ContentImg();
                contentImg.setPost(post);
                saveContentImg(newUrl,contentImg);
            }
        }

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

    /*
    * 새 newContentImgUrl이 기존에 있는지 확인
    * */
    public boolean compareNewToOld(String newContentImgUrl, List<ContentImg> contentImgList){
        for(ContentImg contentImg: contentImgList){
            String oldContentImgUrl = contentImg.getImgUrl();
            if(newContentImgUrl.equals(oldContentImgUrl)){
                return true;
            }
        }

        return false;
    }

    /*
    * 기존에 있는 Url이 newContentImgUrl에 있는지 확인
    * */

    public boolean compareOldToNew(String oldContentImgUrl, List<String> newContentImgUrls){
        for(String newContentImgUrl: newContentImgUrls){
            if(oldContentImgUrl.equals(newContentImgUrl)){
                return true;
            }
        }

        return false;
    }
}
