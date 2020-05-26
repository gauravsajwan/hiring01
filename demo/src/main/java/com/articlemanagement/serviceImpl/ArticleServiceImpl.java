package com.articlemanagement.serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.articlemanagement.model.Article;
import com.articlemanagement.model.TimeToRead;
import com.articlemanagement.repository.ArticleRepository;
import com.articlemanagement.service.ArticleService;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	ArticleRepository articleRepository;

	List<String> list = new ArrayList<String>();

	public Article save(Article article) {

		String[] tags = article.getTags();

		list = Arrays.asList(tags).stream().map(String::toLowerCase).collect(Collectors.toList());

		String tagList[] = list.stream().toArray(String[]::new);

		article.setTags(tagList);

		return articleRepository.save(article);
	}

	public Article fectchById(int id) {
		// TODO Auto-generated method stub
		Optional<Article> article = Optional.of(articleRepository.findOne(id));

		if (article.isPresent())
			return article.get();

		else
			return null;
	}

	@Override
	public Article updateArticle(int id, Article article) {
		Optional<Article> articleData = Optional.of(articleRepository.findOne(id));

		if (articleData.isPresent()) {
			Article _article = articleData.get();
			_article.setTitle(article.getTitle());
			_article.setBody(article.getBody());
			_article.setDescription(article.getDescription());
			_article.setTags(article.getTags());
			_article.setFeaturedImage(article.getFeaturedImage());

			return _article;
		}

		else
			return null;

	}

	@Override
	public Article deleteById(int id) {

		Optional<Article> article = Optional.of(articleRepository.findOne(id));

		if (article.isPresent()) {
			articleRepository.delete(id);
		}

		return null;
	}

	@Override
	public List<Article> getAllArticles() {

		return articleRepository.findAll();
	}

	@Override
	public Integer getTotalWordCount(String completeString) {
		// TODO Auto-generated method stub

		int count = 0;

		char ch[] = new char[completeString.length()];
		for (int i = 0; i < completeString.length(); i++) {
			ch[i] = completeString.charAt(i);
			if (((i > 0) && (ch[i] != ' ') && (ch[i - 1] == ' ')) || ((ch[0] != ' ') && (i == 0)))
				count++;
		}
		return count;

	}

	/**
	 * Using Minimum Edit Distance to find out the score or minimum number of
	 * characters to remove in order to match the another given string
	 */
	@Override
	public int getSimilarityScore(String str1, String str2, int m, int n) {

		// If second string is empty, the only option is to
		// remove all characters of first string
		if (m == 0)
			return n;

		if (n == 0)
			return m;

		// If last characters of two strings are same, nothing
		// much to do. Ignore last characters and get count for
		// remaining strings.
		if (str1.charAt(m - 1) == str2.charAt(n - 1))
			return getSimilarityScore(str1, str2, m - 1, n - 1);

		// If last characters are not same, consider all three
		// operations on last character of first string, recursively
		// compute minimum cost for all three operations and take
		// minimum of three values.
		return 1 + min(getSimilarityScore(str1, str2, m, n - 1), // Insert
				getSimilarityScore(str1, str2, m - 1, n), // Remove
				getSimilarityScore(str1, str2, m - 1, n - 1) // Replace
		);

	}

	static int min(int x, int y, int z) {
		if (x <= y && x <= z)
			return x;
		if (y <= x && y <= z)
			return y;
		else
			return z;
	}

	@Override
	public TimeToRead getTimeToReadArticle(Article article, double words_per_minutes) {
		String articleBody, articleDescription, completeString;
		Integer wordCount = 0;
		TimeToRead timeToRead = new TimeToRead();
		articleBody = article.getBody();
		articleDescription = article.getDescription();
		completeString = articleBody.concat(" " + articleDescription);
		wordCount = getTotalWordCount(completeString);
		System.out.println("--------" + wordCount);
		double timeToReadInMinutes = wordCount / words_per_minutes;
		System.out.println("--------" + timeToReadInMinutes);
		double timeToReadInSeconds = timeToReadInMinutes * 60;
		System.out.println("--------" + timeToReadInSeconds);
		timeToRead.setMinutes(timeToReadInMinutes);
		timeToRead.setSeconds(Math.round(timeToReadInSeconds));

		return timeToRead;
	}

	@Override
	public Page<Article> findAll(Pageable pageable) {

		return articleRepository.findAll(pageable);
	}

}
