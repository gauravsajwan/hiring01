package com.articlemanagement.test;

import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.articlemanagement.model.Article;
import com.articlemanagement.repository.ArticleRepository;
import com.articlemanagement.service.ArticleService;


import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleServiceTest {
	
	
	

	@Autowired
	private ArticleService articleService;
	
	@MockBean
	private ArticleRepository articleRepository;
	
	@Test
	public void testGetTicketById(){
		Article article = new Article();
		String[] tags= {"java", "spring boot","tutorial"};   
		Article mockArticle = new Article();
		mockArticle.setId(1);
		mockArticle.setBody("Updated You have to believe");
		mockArticle.setDescription("Updated Ever wonder how?");
		mockArticle.setTitle("Updated How to learn Spring Booot");
		mockArticle.setTags(tags);
	    Mockito.when(articleRepository.findOne(1)).thenReturn(mockArticle);
	    assertThat(articleService.fectchById(1)).isEqualTo(mockArticle);
	}
	

}
