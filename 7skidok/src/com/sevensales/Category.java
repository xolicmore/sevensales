package com.sevensales;

public class Category {

	public int id;	
	public String name;
		
	
	public Category() {}
	public Category(String id,String name) {		
		this.id=Integer.parseInt(id);		
		this.name=name;		
	};
	
}
