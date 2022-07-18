package com.seo.myblog.Repository;

import com.seo.myblog.entity.Category;
import com.seo.myblog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
