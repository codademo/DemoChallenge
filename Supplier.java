package com.assign.warehouse;

import java.util.ArrayList;
import java.util.List;

public abstract class Supplier<T extends Product> {
	
	private Warehouse warehouse ;
	
	private String id;
	
	private List<ProductType> supportedProducts = new ArrayList<ProductType>();
	
	public Supplier(Warehouse wareHouse){
		this.warehouse=wareHouse;
	}
	
	void supply(int noOfItmes,ProductType pType) throws Exception{
		if(noOfItmes>0 && noOfItmes<Integer.MAX_VALUE){
			//produce the product
			List<T> itemList = new ArrayList<T>();
			for(int i = 0 ; i<noOfItmes ;i++){
				itemList.add(produceItem(pType));
			}
			if(warehouse!=null){
				warehouse.send(itemList);
				System.out.println(noOfItmes + " " + pType +  " items delivered to warehouse by " + this.getId());
			}
			else{
				System.out.println("Supplier is not properly initialized. Please provide a concrete warehouse implementation");
			}
		}
		else{
			System.out.println("Invalid quantity demanded : " + noOfItmes);
		}
	}
	
	public abstract T produceItem( ProductType pType) throws Exception;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 
	 * @return
	 */
	public List<ProductType> getSupportedProductTypes() {
		return new ArrayList<ProductType>(supportedProducts);
	}
	
	public void addSupportedProducts(ProductType prodType){
		supportedProducts.add(prodType);
	}
	
	/**
	 * Add list of supported product types. 
	 * @param prodTypeList
	 */
	public void addSupportedProducts(List<ProductType> prodTypeList){
		if(prodTypeList!=null)
		{
			for(ProductType p : prodTypeList){
				addSupportedProducts(p);
			}
		}
	}
	
}
