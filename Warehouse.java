package com.assign.warehouse;

import java.util.List;

public interface Warehouse {

	void registerSupplier(Supplier<?> supplier);
	
	void registerConsumer(Consumer<?> consumer);
	
	/**
	 * accept itemlist from supplier and update inventory
	 * @param itemList
	 */
	void send(List<? extends Product> itemList);
	
	/**
	 * Deliver noOfItems of PType to consumer from available inventory
	 * @param noOfItems
	 * @param pType
	 * @param consumer
	 */
	public void consume(int noOfItems, ProductType pType, Consumer<?> consumer);

	/**
	 * Get inventory size of this product type in warehouse
	 * @param pType
	 * @return
	 */
	public int getInventorySize(ProductType pType);
}
