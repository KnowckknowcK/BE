package com.knu.KnowcKKnowcK.repository;

import com.knu.KnowcKKnowcK.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Override
    Optional<Article> findById(Long id);
}
