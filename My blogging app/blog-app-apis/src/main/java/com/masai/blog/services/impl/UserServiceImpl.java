package com.masai.blog.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.blog.entities.User;
import com.masai.blog.exceptions.ResourceNotFoundException;
import com.masai.blog.payloads.UserDto;
import com.masai.blog.repositories.UserRepo;
import com.masai.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDto createUser(UserDto userDto) {
		
		User user = this.userDtoToUser(userDto);
		
		User savedUser = userRepo.save(user);
		
		return this.userToUserDto(savedUser);
		 
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		 User user = this.userRepo.findById(userId).orElseThrow(() ->new ResourceNotFoundException("user","Id",userId));
		
		 user.setName(userDto.getName());
		 user.setAbout(userDto.getAbout());
		 user.setEmail(userDto.getEmail());
		 user.setPassword(userDto.getPassword());
		 
		 User updatedUser = this.userRepo.save(user);
		 
		 return this.userToUserDto(updatedUser);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		
		User user = this.userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));

		return this.userToUserDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		
		List<User> users = this.userRepo.findAll();
		
		List<UserDto> userDtos = new ArrayList<>();
		
		for(User u:users) {
			UserDto uDto = this.userToUserDto(u);
			userDtos.add(uDto);
		}
		 
		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "Id", userId));

		this.userRepo.delete(user);

	}
	
	
	private User userDtoToUser(UserDto userDto) {
		
		
		
//		User user = new User();
		
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setPassword(userDto.getPassword());
//		user.setAbout(userDto.getAbout());
		 
		
//		return user;


		User user = this.modelMapper.map(userDto, User.class);
		return user;
	}
	
	private UserDto userToUserDto(User user) {
		
//		UserDto userDto = new UserDto();
//		
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setPassword(user.getPassword());
//		userDto.setAbout(user.getAbout());
//		 
//		
//		return userDto;
		
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		return userDto;
	}

}
