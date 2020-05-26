package com.articlemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.articlemanagement.model.Article;

public interface ArticleRepository  extends JpaRepository<Article, Integer>{

}
