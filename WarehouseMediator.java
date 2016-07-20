package com.assign.warehouse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * This class provides the functionality to register supplier and consumers of a warehouse. 
 * @author atul mittal
 *
 */
public class WarehouseMediator implements Warehouse {

	private ConcurrentMap<ProductType,List<Supplier<?>>> productSupplierMap = new ConcurrentHashMap<ProductType, List<Supplier<?>>>();
	
	private ConcurrentMap<ProductType,List<Consumer<?>>> productConsumerMap = new ConcurrentHashMap<ProductType, List<Consumer<?>>>();
	
	private ConcurrentMap<String,Supplier<?>> supplierMap = new ConcurrentHashMap<String, Supplier<?>>();
	
	private ConcurrentMap<String,Consumer<?>> consumerMap = new ConcurrentHashMap<String, Consumer<?>>();
	
	private ConcurrentMap<ProductType,ArrayBlockingQueue<Product>> productTypeProductInventoryMap = new ConcurrentHashMap<ProductType, ArrayBlockingQueue<Product>>();
	
	
	/**
	 * Registers a supplier if it is not registered.
	 */
	public void registerSupplier(Supplier<?> supplier) {
		
		if(supplier.getId()== null || ( supplier.getId()!=null && supplierMap.get(supplier.getId())==null )){
			
			String supplierId = UUID.randomUUID().toString();
			
			supplier.setId(supplierId);
			
			supplierMap.put(supplierId, supplier);
			
			List<ProductType> supportedProductTypes = supplier.getSupportedProductTypes();
			
			if(supportedProductTypes!=null){
				
				for(ProductType pType : supportedProductTypes){
					List<Supplier<?>> supplierList = productSupplierMap.get(pType);
					if(supplierList==null){
						supplierList = new ArrayList<Supplier<?>>();
						productSupplierMap.put(pType, supplierList);
					}
					supplierList.add(supplier);	
				}
				
			}
		}
		
		else {
			System.out.println(supplier + " supplier is already registered");
		}
	}

	/**
	 * Registers a consumer if it is not registered.
	 */
	public void registerConsumer(Consumer<?> consumer) {
		
		if(consumer.getId()==null || (consumer.getId()!=null && consumerMap.get(consumer.getId())==null)) {
			
			String consumerId = UUID.randomUUID().toString();
			
			consumer.setId(consumerId);
			
			consumerMap.put(consumerId, consumer);
			
			List<ProductType> supportedProductTypes = consumer.getSupportedProductTypes();
			
			if(supportedProductTypes!=null){
				
				for(ProductType pType : supportedProductTypes){
					List<Consumer<?>> consumerList = productConsumerMap.get(pType);
					if(consumerList==null){
						consumerList = new ArrayList<Consumer<?>>();
						productConsumerMap.put(pType, consumerList);
					}
					consumerList.add(consumer);	
				}
				
			}
		}
		else{
			System.out.println(consumer + " consumer is already registered");
		}

	}

	public void send(List<? extends Product> itemList) {
		if(itemList!=null){
			for(Product p : itemList){
				ArrayBlockingQueue<Product> productQueue = productTypeProductInventoryMap.get(p.getProdType());
				if(productQueue==null){
					productQueue = new ArrayBlockingQueue<Product>(1000);
					productTypeProductInventoryMap.put(p.getProdType(), productQueue);
				}
				try {
					productQueue.put(p);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public void consume(int noOfItems, ProductType pType, Consumer<?> consumer){
		
		List<Consumer<?>> consumerList = productConsumerMap.get(pType);
		
		if(consumerList!=null && consumerList.contains(consumer)){
			ArrayBlockingQueue<Product> productQueue = productTypeProductInventoryMap.get(pType);
			if(productQueue!=null && productQueue.size()>=noOfItems){
				for(int i=0;i<noOfItems;i++){
					consumer.consume(productQueue.poll());
				}
			}
			else{
				System.out.println(pType + " productType is not avaialable in warehouse :  " + consumer.getId());
			}
		}
		else{
			System.out.println(consumer.getId() + " is not supported for " + pType);
		}
	}
	
	public Set<ProductType> getAvailableProductTypes(){
		return new HashSet<ProductType>(productTypeProductInventoryMap.keySet());
	}

	public List<Supplier<?>> getAvailableSuppliers(){
		
		Collection<List<Supplier<?>>> supplierLists = productSupplierMap.values();
		List<Supplier<?>> supplierList = new ArrayList<Supplier<?>>();
		if(supplierLists!=null){
			
			for(List<Supplier<?>> s : supplierLists){
				supplierList.addAll(s);
			}
		}
		return supplierList;
	}

	public int getInventorySize(ProductType pType){
		
		ArrayBlockingQueue<Product> pQueue = productTypeProductInventoryMap.get(pType);
		if(pQueue !=null){
			return pQueue.size();
		}
		return 0;
	}
	
}
