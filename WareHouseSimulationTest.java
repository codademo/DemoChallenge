package com.assign.warehouse;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

public class WareHouseSimulationTest {
	
	private Warehouse warehouse;
	
	private WarehouseSimulator simulator;
	
	@Before
	public void setup(){
		warehouse=new WarehouseMediator();
		simulator= new WarehouseSimulator();
	}

	@Test
	public void testWareHouseSimulation(){
		simulator.simulate(warehouse);
		Assert.assertEquals("size of rice is not matching ", 5, warehouse.getInventorySize(ProductType.RICE));
	}
}
