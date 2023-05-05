package com.masai.blog.exceptions;

import java.util.function.Supplier;

public class CategoryNotFoundException extends RuntimeException {
 
	public CategoryNotFoundException() {
		// TODO Auto-generated constructor stub
	}
	
	public CategoryNotFoundException(String message) {
		super(message);
	}
	

}
