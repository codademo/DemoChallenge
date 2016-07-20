package com.assign.warehouse;

public class SupplierFactory extends Supplier<Product> {

	public SupplierFactory(Warehouse wareHouse) {
		super(wareHouse);
	}

	@Override
	public Product produceItem(ProductType pType) throws Exception {
		Product p = null;
		if(this.getSupportedProductTypes().contains(pType)){
			p = new Product();
			p.setProdType(pType);
		}
		else{
			throw new Exception("Unsupported Product Type : " + pType.toString());
		}
		return p;
	}

}
