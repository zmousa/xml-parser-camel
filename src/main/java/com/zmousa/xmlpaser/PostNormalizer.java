package com.zmousa.xmlpaser;

import java.util.ArrayList;
 
public class PostNormalizer {
	private Post post;
	
	public PostNormalizer(Post post) {
		this.post = post;
	}
	
	public Post normlize(){
		cleanTags();
		cleanTitle();
		cleanBody();
		return post;
	}
	
	private void cleanTags(){
		post.setTags(post.getTags().toLowerCase()
				.replaceAll("><",",")
				.replaceAll("[<>]",""));
	}
	
	private String cleanText(String text){
		text = text.toLowerCase()
				.replaceAll("[\\p{Punct}||\\p{Cntrl}&&[^.'-]]"," ")
				.replaceAll(" +",",");
		String[] words = text.split(",");
		ArrayList<String> normalizedWords = new ArrayList<String>();
		for(String word : words)
	        if(! ParsingRoute.stopWords.contains(word) && !word.matches("[0-9\\p{Punct}]*"))
	        	normalizedWords.add(word.replaceAll("^[.']+|[.']+$",""));
		
		text = String.join(",", normalizedWords);
		return text;
	}
	
	private void cleanTitle(){
		post.setTitle(cleanText(post.getTitle()));
	}
	
	private void cleanBody(){
		post.setBody(cleanText(post.getBody()));
	}
}