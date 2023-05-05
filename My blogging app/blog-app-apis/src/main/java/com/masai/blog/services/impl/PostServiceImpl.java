package com.masai.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.masai.blog.entities.Category;
import com.masai.blog.entities.Post;
import com.masai.blog.entities.User;
import com.masai.blog.exceptions.ResourceNotFoundException;
import com.masai.blog.payloads.PostDto;
import com.masai.blog.payloads.PostResponse;
import com.masai.blog.repositories.CategoryRepo;
import com.masai.blog.repositories.PostRepo;
import com.masai.blog.repositories.UserRepo;
import com.masai.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService{
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelmapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "user id", userId));
		
		
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "category id", categoryId));
		
		
		Post post = this.modelmapper.map(postDto, Post.class);
		
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		Post savedPost = this.postRepo.save(post);
		
		return this.modelmapper.map(savedPost, PostDto.class);
 
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Post id", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		
		Post updatedPost = this.postRepo.save(post);
		
		return this.modelmapper.map(updatedPost, PostDto.class);
		
	}

	@Override
	public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy) {
		
	     Pageable p = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
	 
	     Page<Post> pagePost = this.postRepo.findAll(p);
	     
	     
	  
	     List<Post> posts = pagePost.getContent();
	  
	     List<PostDto> postDtos = posts.stream().map((post)->this.modelmapper.map(post, PostDto.class))
			 .collect(Collectors.toList());
	 
	     PostResponse postResponse = new PostResponse();
		 
		 postResponse.setContent(postDtos);
		 postResponse.setPageNumber(pagePost.getNumber());
		 postResponse.setPageSize(pagePost.getSize());
		 postResponse.setTotalElements(pagePost.getTotalElements());
		 postResponse.setTotalPages(pagePost.getTotalPages());
		 postResponse.setLastPage(pagePost.isLast());
		 
		 
		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Post id", postId));
		 
		return this.modelmapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getAllPostByCategory(Integer categoryId) {
		 
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "category id",categoryId));
		List<Post> posts = this.postRepo.findByCategory(category);
		
		List<PostDto> postDtos = posts.stream().map((post)->this.modelmapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postDtos;
	}

	@Override
	public List<PostDto> getAllPostByUser(Integer userId) {
		 User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "user id", userId));
		 List<Post> posts = this.postRepo.findByUser(user);
		 
		 List<PostDto> postDtos = posts.stream().map((post)-> this.modelmapper.map(post, PostDto.class)).collect(Collectors.toList());
		 
		 return postDtos;
	}

	@Override
	public List<Post> searchPosts(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deletePostById(Integer postId) {
		 
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post", "post id", postId));
		
		this.postRepo.delete(post);
		
	}

}
