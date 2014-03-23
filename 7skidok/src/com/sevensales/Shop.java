package com.sevensales;

public class Shop {
	
	public Shop() {};
	//public int id;
	public int id;
	public String name;
	//public String category;
	public String description;
	
	
	public Shop Shop_test(int shop_id,String shop_name,String shop_description) {
		this.id = shop_id;
		this.name=shop_name;
		this.description=shop_description;
		
		return this;
	}

}
