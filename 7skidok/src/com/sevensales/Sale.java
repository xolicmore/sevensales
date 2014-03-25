package com.sevensales;

public class Sale {
	
	public Sale() {};
	//public int id;
	public int shop_id;
	public String shop_name;
	//public String category;
	public String description;
	
	
	public Sale Sale_test(int shop_id,String shop_name,String description) {
		this.shop_id = shop_id;
		this.shop_name=shop_name;
		this.description=description;
		
		return this;
	}
	

}
