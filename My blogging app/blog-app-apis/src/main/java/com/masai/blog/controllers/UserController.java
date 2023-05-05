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
import org.springframework.web.bind.annotation.RestController;

import com.masai.blog.payloads.ApiResponse;
import com.masai.blog.payloads.UserDto;
import com.masai.blog.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/myblog/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	
	
	//Post- create user
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		UserDto savedUser  = this.userService.createUser(userDto);
		return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
	}
	
	//PUT-update user
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable("userId") Integer uId){
		
		UserDto updateUserDto = this.userService.updateUser(userDto, uId);
//		return new ResponseEntity<>(updateUserDto,HttpStatus.OK);
		return ResponseEntity.ok(updateUserDto);
		
	}
	
	//DELETE delete user
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer uid){
		this.userService.deleteUser(uid);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Successfully", true), HttpStatus.OK);
		
		
	}
	//GET- user get
	@GetMapping("/getallusers")
	public ResponseEntity<List<UserDto>> getAllUsers(){
		
		List<UserDto> userList = this.userService.getAllUsers();
		
		return new ResponseEntity<List<UserDto>>(userList, HttpStatus.OK);
		
	}
	
	@GetMapping("/{userid}")
	public ResponseEntity<UserDto> getUserById(@PathVariable("userid") Integer userId){
		
		 UserDto user = this.userService.getUserById(userId);
		
		return new ResponseEntity<>(user, HttpStatus.OK);
		
	}

}
