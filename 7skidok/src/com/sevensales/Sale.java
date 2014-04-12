package com.sevensales;

public class Sale {
	
	public int id;
	public int shop_id;
	public String name;
	public String short_name;	
	public String shop_name;
	public String date_start;
	public String date_end;
	public String left_sec;
	public String img_small;
	public String img_big;
	public Category[] categories;
	
	public String description;	
	
	public Sale() {}
	public Sale(String id,String name,String short_name,
			String shop_id,String shop_name,String date_start,
			String date_end,String left_sec,String img_small,String img_big,Category[] categories) {
		
		this.id=Integer.parseInt(id);		
		this.name=name;
		this.short_name=short_name;
		this.shop_id=Integer.parseInt(shop_id);
		this.shop_name=shop_name;
		this.date_start=date_start;
		this.date_end=date_end;
		this.left_sec=left_sec;
		this.img_small=img_small;
		this.img_big=img_big;
		this.description=name;
		this.categories=categories;
	};
		
	
	
	
	
	public Sale Sale_test(int shop_id,String shop_name,String description) {
		this.shop_id = shop_id;
		this.shop_name=shop_name;
		this.description=description;
		
		return this;
	}
	
	public void setCategories(Category[] a){
		this.categories=a;
	}
	

}
