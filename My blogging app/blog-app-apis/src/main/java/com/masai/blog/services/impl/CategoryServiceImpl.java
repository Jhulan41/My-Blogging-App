package com.masai.blog.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.masai.blog.entities.Category;
import com.masai.blog.exceptions.CategoryNotFoundException;
import com.masai.blog.exceptions.ResourceNotFoundException;
import com.masai.blog.payloads.CategoryDto;
import com.masai.blog.repositories.CategoryRepo;
import com.masai.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper  modelMapper;

	@Override
	public CategoryDto crateCategory(CategoryDto categoryDto) {
		
		Category category = this.modelMapper.map(categoryDto, Category.class);		
		Category savedCategory = categoryRepo.save(category); 		
		return this.modelMapper.map(savedCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		
		 Category cat = this.categoryRepo.findById(categoryId)
		 .orElseThrow(() -> new  ResourceNotFoundException("Category", "Category Id", categoryId));
		 
		 cat.setCategoryDescription(categoryDto.getCategoryDescription());
		 cat.setCategoryTitle(categoryDto.getCategoryTitle());
		 
		 Category updatedCat = this.categoryRepo.save(cat);
		 
		return this.modelMapper.map(updatedCat, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		 Category cat = this.categoryRepo.findById(categoryId)
				 .orElseThrow(() -> new  ResourceNotFoundException("Category", "Category Id", categoryId));
		 
		 this.categoryRepo.delete(cat);

	}

	@Override
	public CategoryDto getCategoryById(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId)
				 .orElseThrow(() -> new  ResourceNotFoundException("Category", "Category Id", categoryId));
		 
		return this.modelMapper.map(cat, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		
		//using forEach
		
//		List<CategoryDto> list = new ArrayList<>();
//		
//		this.categoryRepo.findAll().forEach((c)->{
//			list.add(this.modelMapper.map(c, CategoryDto.class));
//			
//		});
		
		//using streamApi...
		
		List<Category> categories = this.categoryRepo.findAll();
		List<CategoryDto> catDtos = categories.stream().map((cat)->this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
		
		return catDtos;
	}

}
