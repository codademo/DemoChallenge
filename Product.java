package com.assign.warehouse;

public class Product {
	
	private ProductType prodType;

	public ProductType getProdType() {
		return prodType;
	}

	public void setProdType(ProductType prodType) {
		this.prodType = prodType;
	}

	@Override
	public String toString() {
		return "Product [prodType=" + prodType + "]";
	}

}
