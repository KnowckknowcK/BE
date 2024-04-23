package com.knu.KnowcKKnowcK.repository;

import com.knu.KnowcKKnowcK.domain.Article;
import com.knu.KnowcKKnowcK.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Override
    Optional<Article> findById(Long id);

    List<Article> findByCategory(Category category);

    Optional<Article> findTop1ByCategory(Category category);


}
