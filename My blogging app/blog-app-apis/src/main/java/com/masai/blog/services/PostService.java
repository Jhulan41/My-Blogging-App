package com.masai.blog.services;

import java.util.List;

import com.masai.blog.entities.Post;
import com.masai.blog.payloads.PostDto;
import com.masai.blog.payloads.PostResponse;

public interface PostService {
	
	//create
	 PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
	
	//update
	 
	 PostDto updatePost(PostDto postDto, Integer postId);
	 
	 //delete post
	 void deletePostById(Integer postId);
	
	// get all post
	 PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy);
	
	//get post by postId
	 
	 PostDto getPostById(Integer postId);
	
	//get all post by category 
	 
	 List<PostDto> getAllPostByCategory(Integer categoryId);
	
	//get all post by user
	 List<PostDto> getAllPostByUser(Integer userId);
	 
	 //search posts
	 
	 List<Post> searchPosts(String keyword);
	

}
