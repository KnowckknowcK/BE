package com.knu.KnowcKKnowcK.repository;

import com.knu.KnowcKKnowcK.domain.Article;
import com.knu.KnowcKKnowcK.enums.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Override
    Optional<Article> findById(Long id);

    Page<Article> findByCategory(Category category, Pageable pageable);

    Optional<Article> findTop1ByCategory(Category category);


}
