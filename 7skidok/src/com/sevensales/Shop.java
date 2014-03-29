package com.sevensales;

import java.util.Arrays;

public class Shop {
	
	
	
	public int id;	
	public String name;
	public int sales;
	public String img_url;
	public Category[] categories;
	
	public String description;
	
	public Shop() {}
	public Shop(String id,String name,String sales,
			String img_url,Category[] categories) {
		
		this.id=Integer.parseInt(id);		
		this.name=name;
		this.sales=Integer.parseInt(sales);
		this.img_url=img_url;		
		this.description=name;
		this.categories=categories;	
	};
	
	public void setCategories(Category[] a){
		this.categories=a;
	}
	
	
	public Shop Shop_test(int shop_id,String shop_name,String shop_description) {
		this.id = shop_id;
		this.name=shop_name;
		this.description=shop_description;
		
		return this;
	}

}
