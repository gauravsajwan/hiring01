package com.articlemanagement.service;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.data.domain.Page;

import com.articlemanagement.model.Article;
import com.articlemanagement.model.TimeToRead;

public interface ArticleService {

	Article save(Article article);

	Article fectchById(int id);

	Article updateArticle(int id, Article article);

	Article deleteById(int id);

	Integer getTotalWordCount(String completeString);

	int getSimilarityScore(String str1, String str2, int m, int n);

	TimeToRead getTimeToReadArticle(Article article, double words_per_minutes);

	List<Article> getAllArticles();

	Page<Article> findAll(org.springframework.data.domain.Pageable pageable);

}
