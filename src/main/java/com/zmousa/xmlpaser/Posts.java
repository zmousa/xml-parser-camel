package com.zmousa.xmlpaser;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
 
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "posts")
public class Posts {
    @XmlElement(name = "row")
    protected List<Post> posts;

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
}