package com.seo.myblog.Repository;

import com.seo.myblog.dto.CategoryDTO;
import com.seo.myblog.entity.Category;
import com.seo.myblog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    @Query("select c.id, c.name, count (p.id) " +
            "from Post p left join Category c on p.category = c " +
            "group by p.category " +
            "order by c.name desc")
    List<Object[]> findCategoryInfo(); //모든 카테고리 id, 이름, 카테고리 내 글 갯수 구하는 쿼리

}
