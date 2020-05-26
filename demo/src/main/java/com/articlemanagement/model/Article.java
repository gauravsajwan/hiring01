package com.articlemanagement.model;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "article")

public class Article {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id; 
	
	@NotNull
	private String title;
	@NotNull
	private String description;
	@NotNull
	private String body;
	private String[] tags;
	private String featuredImage;
	
	@OneToOne
	@JoinColumn(name="Time_to_ReadArticle")
	private TimeToRead timeToRead;
	
	public Article()
	{
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String[] getTags() {
		return tags;
	}
	public void setTags(String[] tags) {
		this.tags = tags;
	}
	public String getFeaturedImage() {
		return featuredImage;
	}
	public void setFeaturedImage(String featuredImage) {
		this.featuredImage = featuredImage;
	}
	

	public TimeToRead getTimeToRead() {
		return timeToRead;
	}
	public void setTimeToRead(TimeToRead timeToRead) {
		this.timeToRead = timeToRead;
	}
	
	
	
	
	
	
}
