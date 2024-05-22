package com.knu.KnowcKKnowcK.repository;

import com.knu.KnowcKKnowcK.domain.Article;
import com.knu.KnowcKKnowcK.enums.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Override
    Optional<Article> findById(Long id);

    Page<Article> findByCategory(Category category, Pageable pageable);


    @Query(value = "SELECT t.* FROM " +
            "(SELECT a.*, row_number() over (partition by a.category) AS row_num " +
            "FROM article a) AS t " +
            "WHERE t.row_num =1"
            ,nativeQuery = true)
    List<Article> find1ByCategory();


}
