package com.articlemanagement.controller;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.articlemanagement.model.Article;
import com.articlemanagement.model.Tags;
import com.articlemanagement.model.TimeToRead;
import com.articlemanagement.service.ArticleService;

@RestController
@RequestMapping("/api")
public class ArticleController {

	@Autowired
	ArticleService articleService;

	/**
	 * Configurable speed of average human for reading words per minute
	 * 
	 */

	@Value("${words_per_minute_speed}")
	public double words_per_minutes;

	/**
	 * Story 1: REST API to create an article
	 * 
	 * Story 8: A user should not be able to submit article with same body twice
 
	 * 
	 * @param article
	 * @return
	 */
	@PostMapping(value = "/articles")
	public ResponseEntity<Article> save(@Valid @RequestBody Article article) {

		try {

			List<Article> articleList = articleService.getAllArticles();
			for (Article _article : articleList) {
				/**
				 * Story 8: A user should not be able to submit article with same body twice
 
				 * Check for Article Body Similarity Using Minimum Edit Distance Algorithm
				 * Lesser the score is more similar the string is and vice-versa If Found Same
				 * throws 404- Page Not Found status
				 */
				int m = article.getBody().length();
				int n = _article.getBody().length();
				double score = articleService.getSimilarityScore(article.getBody(), _article.getBody(), m, n);

				int maxLength = Math.max(m, n);
				double scorePercentage = ((score / maxLength) * 100);

				if (scorePercentage < 70)
					return new ResponseEntity<Article>(article, HttpStatus.NOT_FOUND);

			}
			article = articleService.save(article);
			return new ResponseEntity<Article>(article, HttpStatus.CREATED);

		} catch (Exception e) {
			return new ResponseEntity<Article>(article, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * Story 3: Get an article Rest API to fetch the article by articleId as a
	 * request Param
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/article")
	public ResponseEntity<Article> fectchById(@RequestParam int id) {

		Article article = articleService.fectchById(id);
		System.out.println();
		if (article == null)
			return ResponseEntity.notFound().build();

		else

			return ResponseEntity.ok().body(article);
	}

	/**
	 * Story 6: Find time to read for an article
	 * 
	 * @param id
	 * @return
	 */

	@GetMapping(value = "/article/{id}/timetoread")
	public ResponseEntity<Article> timeToReadArticle(@PathVariable("id") int id) {

		Article article = articleService.fectchById(id);

		if (article == null)
			return ResponseEntity.notFound().build();

		else {
			/**
			 * get Time to read the article of Each Article ID
			 */
			TimeToRead timeToRead = articleService.getTimeToReadArticle(article, words_per_minutes);
			article.setTimeToRead((timeToRead));
			return ResponseEntity.ok().body(article);

		}
	}

	/**
	 * Story 4: List articles without Pagination
	 * 
	 * @return
	 */
@GetMapping(value = "/articles")
	public ResponseEntity<List<Article>> getAllArticles() {

		List<Article> articles = articleService.getAllArticles();
		articles.forEach(System.out::println);
		return new ResponseEntity<List<Article>>(articles, HttpStatus.OK);

	}

/**
 * Story 4: List articles along with Pagination
 * 
 * @return
 */
@GetMapping(value = "/listPageableArticles")
Page<Article> articlesPageable(Pageable pageable) {
	return articleService.findAll(pageable);

}

/**
 * Story 2: Update an article 
 * 
 * 
 * @param article
 * @param id
 * @return
 */

	@PutMapping("/articles/{id}")
	public ResponseEntity<Article> updateArticle(@RequestBody Article article, @PathVariable int id) {
		Article updatedArticle = articleService.updateArticle(id, article);
		if (updatedArticle == null)
			return new ResponseEntity<Article>(HttpStatus.NOT_FOUND);
		else {
			String response = "{\"success\": true, \"message\": Article has been updated successfully.}";
			System.out.println(response);
			return new ResponseEntity<Article>(articleService.save(updatedArticle), HttpStatus.OK);
		}

	}

	/**
	 * Story 5: Delete an article
	 * 
	 * @param id
	 * @return
	 */

	@DeleteMapping("/articles/{id}")
	public ResponseEntity<HttpStatus> deleteArticle(@PathVariable("id") int id) {
		try {
			articleService.deleteById(id);
			String response = "{\"success\": true, \"message\": Article has been deleted successfully.}";
			System.out.println(response);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
	}

	/**
	 * Story 7: Generate Tag based metrics
	 * 
	 * 
	 * @return
	 */
	@GetMapping("/articles/tags")
	public List<String> getAllTagsOccurence() {
		Tags tagFrequency = new Tags();

		List<Article> articles = articleService.getAllArticles();
		List<String> tagList = new ArrayList<String>();
		Iterator<Article> articleIterator = articles.iterator();

		while (articleIterator.hasNext()) {
			Article article = articleIterator.next();
			String[] tags = article.getTags();

			for (String tag : tags) {
				System.out.println("@@@@@@@@@@@@" + tag);
				tagList.add(tag);

			}

		}

		tagList.forEach(System.out::println);

		/**
		 * To find the frequence of words present in the taglist
		 */
		ConcurrentMap<Object, Integer> map = tagList.stream()
				.collect(Collectors.toConcurrentMap(w -> w, w -> 1, Integer::sum));

		map.forEach((k, v) -> System.out.println("Tag = " + k + ", Occurence = " + v));

		/**
		 * First approach is to return the map itself with the key as a tag and Value as
		 * Frequency Occurence
		 */
		// return map;

		/**
		 * Second Approach is to create a Tag Entity and set the tag and Occuence in the
		 * Tag Object and return it
		 */

		/*
		 * List<Tags> listOfTags=null; for (Entry<Object, Integer> entry :
		 * map.entrySet()) {
		 * 
		 * listOfTags = new ArrayList<Tags>(); tagFrequency.setTag((String)
		 * entry.getKey()); tagFrequency.setOccurence(entry.getValue());
		 * 
		 * listOfTags.add(tagFrequency);
		 * 
		 * for (Tags tags : listOfTags) { System.out.println(tags.getTag());
		 * System.out.println(tags.getOccurence());
		 * 
		 * 
		 * }
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * } return listOfTags;
		 */

		/**
		 * Third Approach is to return the list of String prior fetching the key as a
		 * tag and value as an occurence and return the list
		 */
		List<String> _tagList = new ArrayList<String>();
		for (Entry<Object, Integer> entry : map.entrySet()) {

			String tags = (String) entry.getKey();
			String occurence = Integer.toString(entry.getValue());

			_tagList.add("Tag :" + "'" + tags + "'" + "," + " " + "Occurence:" + "'" + occurence + "'");

		}

		return _tagList;

	}

}
