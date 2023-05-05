package com.masai.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.masai.blog.payloads.ApiResponse;
import com.masai.blog.payloads.PostDto;
import com.masai.blog.payloads.PostResponse;
import com.masai.blog.services.PostService;

@RestController
@RequestMapping("/myblog")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	//create
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(
			@RequestBody PostDto postdto, 
			@PathVariable Integer userId,
			@PathVariable Integer categoryId)
	{
		
		PostDto createPost = this.postService.createPost(postdto, userId, categoryId);
		
		return new ResponseEntity<PostDto>(createPost,HttpStatus.CREATED);
		
		
	}
	
	//get All posts by userId
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getAllPostsByUser(@PathVariable Integer userId){
		List<PostDto> allPostByUser = this.postService.getAllPostByUser(userId);
		
		return new ResponseEntity<List<PostDto>>(allPostByUser,HttpStatus.OK);
	}
	
	//get all posts by categoryId
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getAllPostsByCategory(@PathVariable Integer categoryId){
		List<PostDto> posts = this.postService.getAllPostByCategory(categoryId);
		
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
	//get post by postId
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostByPostId(@PathVariable Integer postId){
		PostDto postDto = this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(postDto, HttpStatus.OK);
	}
	//get all post
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value="pageNumber", defaultValue= "0", required=false)Integer pageNumber,
			@RequestParam(value="pageSize", defaultValue= "10", required=false) Integer pageSize,
			@RequestParam(value="sortBy", defaultValue="postId", required=false) String sortBy
			){
		 PostResponse postResponse = this.postService.getAllPosts(pageNumber, pageSize, sortBy);
		
		return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
	}
	
	//delete post
	@DeleteMapping("/posts/{postId}")
	public ApiResponse deletePost(@PathVariable Integer postId){
		
		this.postService.deletePostById(postId);
 
		return new ApiResponse("Post deleted successfully",true);
	}
	
	//update post	
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postdto, @PathVariable Integer postId){
		
		PostDto updatedPost = this.postService.updatePost(postdto, postId);
 
		return new ResponseEntity<PostDto>( updatedPost,HttpStatus.OK);
	}
	
	

}
