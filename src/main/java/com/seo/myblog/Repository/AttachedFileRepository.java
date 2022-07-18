package com.seo.myblog.Repository;

import com.seo.myblog.entity.AttachedFile;
import com.seo.myblog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachedFileRepository extends JpaRepository<AttachedFile,Long> {
}
