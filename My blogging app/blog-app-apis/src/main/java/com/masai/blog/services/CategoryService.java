package com.masai.blog.services;

import java.util.List;

import com.masai.blog.payloads.CategoryDto;

public interface CategoryService {
	
	
    //Create
	public CategoryDto crateCategory(CategoryDto categoryDto);
	
	//Update
	 CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);
	
	//delete
	 void  deleteCategory(Integer categoryId);
	
	//get
	
	CategoryDto getCategoryById(Integer categoryId);
 
	//getAll
	
	List<CategoryDto> getAllCategory();

}
