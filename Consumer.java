package com.assign.warehouse;

import java.util.ArrayList;
import java.util.List;

public class Consumer<T extends Product> {
	
	private Warehouse warehouse ;
	
	private String id;
	
	private List<ProductType> supportedProducts = new ArrayList<ProductType>();
	
	public Consumer(Warehouse wareHouse){
		this.warehouse=wareHouse;
	}
	
	
	public void consume(Product product){
		if(getSupportedProductTypes().contains(product.getProdType())){
			System.out.println(product + " consumed by " + this.getId()) ;
		}
	}
	
	
	public String getId(){
		return this.id;
	}

	public void setId(String id){
		this.id= id;
	}

	public List<ProductType> getSupportedProductTypes(){
		return supportedProducts;
	}
	
	public void addSupportedProducts(ProductType prodType){
		supportedProducts.add(prodType);
	}
}
