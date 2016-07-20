package com.assign.warehouse;

import java.util.Arrays;

public class WarehouseSimulator {

	public  void simulate(Warehouse warehouse) {
		Supplier<Product> supplier1 = new SupplierFactory(warehouse);
		supplier1.addSupportedProducts(Arrays.asList(ProductType.RICE));
		warehouse.registerSupplier(supplier1);
		
		try {
			supplier1.supply(10, ProductType.RICE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Consumer<Product> consumer1 = new Consumer<Product>(warehouse);
		consumer1.addSupportedProducts(ProductType.CORN);
		warehouse.registerConsumer(consumer1);
		
		Consumer<Product> consumer2 = new Consumer<Product>(warehouse);
		consumer2.addSupportedProducts(ProductType.RICE);
		warehouse.registerConsumer(consumer2);
		
		warehouse.consume(5, ProductType.RICE, consumer1);
		
		warehouse.consume(5, ProductType.CORN, consumer1);
		
		warehouse.consume(5, ProductType.RICE, consumer2);
		
		warehouse.consume(5, ProductType.CORN, consumer2);
	}

}
