package jpmc.message.service;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;

import jpmc.message.entity.AdjustmentMessage;
import jpmc.message.entity.DetailedMessage;
import jpmc.message.entity.OperationType;

public class SalesServiceImplTest {
	
	SalesService underTest=new  SalesServiceImpl();

	@Test
	public void testAddSale() {
		boolean isSaleAdded=underTest.addSale(new DetailedMessage("apple", BigDecimal.TEN, 10));
		assertTrue(isSaleAdded);
	}

	
	@Test
	public void testAddNegativeSaleMessage() {
		boolean isSaleAdded=underTest.addSale(new DetailedMessage("banana", BigDecimal.ONE, -5));
		assertFalse(isSaleAdded);
	}
	
	@Test
	public void testAddAdjustmentSaleMessage() {
		boolean isSaleAdded=underTest.addSale(new AdjustmentMessage("banana", BigDecimal.ONE, OperationType.SUBTRACT));
		assertTrue(isSaleAdded);
	}
	
	
	@Test
	public void testGetSalesByProductType() {
		underTest.addSale(new DetailedMessage("apple", BigDecimal.TEN, 10));
		underTest.addSale(new DetailedMessage("banana", BigDecimal.TEN, 5));
		underTest.addSale(new DetailedMessage("orange", BigDecimal.TEN, 4));
		underTest.addSale(new DetailedMessage("apple", BigDecimal.TEN, 2));
		
		int size=underTest.getSalesByProductType().get("apple").size();
		assertFalse(size==12);
	}

}
