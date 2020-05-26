package com.articlemanagement;

import javax.print.attribute.standard.DateTimeAtCompleted;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
@Configuration
@ComponentScan("classpath:application.properties")
@Component
public class MyBean {

	@Value("${words_per_minute_speed}")
	public static String words_per_minutes;
	
	@Value("${server.port}")
	private static String serverport;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println(serverport);
		String articleBody, articleDescription,completeString;
		 

		articleBody = "Gaurav";
		articleDescription= "Sajwan";
		completeString=articleBody.concat(" "+articleDescription);
		int wordCount=getTotalWordCount(completeString);
		System.out.println(wordCount);
		System.out.println(words_per_minutes);
		double TimetoRead_In_Minutes = 900/300;

		double TimetoRead_In_Seconds = TimetoRead_In_Minutes*60;
		System.out.println(TimetoRead_In_Minutes);
		System.out.println(TimetoRead_In_Seconds);
		System.out.println(completeString);
	}

	private static int getTotalWordCount(String completeString) {
	
	
			 int count=0;  
		      
	         char ch[]= new char[completeString.length()];     
	         for(int i=0;i<completeString.length();i++)  
	         {  
	             ch[i]= completeString.charAt(i);  
	             if( ((i>0)&&(ch[i]!=' ')&&(ch[i-1]==' ')) || ((ch[0]!=' ')&&(i==0)) )  
	                 count++;  
	         }  
	         return count;  
			
		}
	}

