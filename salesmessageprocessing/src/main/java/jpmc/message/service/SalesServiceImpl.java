package jpmc.message.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jpmc.message.dao.SalesDao;
import jpmc.message.dao.SalesDaoImpl;
import jpmc.message.entity.AdjustmentMessage;
import jpmc.message.entity.DetailedMessage;
import jpmc.message.entity.Message;
import jpmc.message.entity.OperationType;
import jpmc.message.entity.Sale;

/**
 * Created by Siddhant Sinha on 12/01/2018.
 */
public class SalesServiceImpl implements SalesService {

	SalesDao dao = null;

	public SalesServiceImpl() {
		dao = new SalesDaoImpl();
	}

	private List<Sale> getSales() {
		return dao.getSales();
	}

	public boolean addSale(Message message) {

		boolean isSaleAdded = true;
		if (message instanceof AdjustmentMessage) {
			List<Sale> totalSales = getSales();
			AdjustmentMessage adjustmentMessage = (AdjustmentMessage) message;
			String typeOfSale = adjustmentMessage.getType();
			// filter sales based on type
			List<Sale> salesToBeAdjusted = totalSales.stream().filter(sale -> sale.getProductType().equals(typeOfSale))
					.collect(Collectors.toList());
			// adjust all the sales as per adjustment message
			isSaleAdded = adjustSale(salesToBeAdjusted, adjustmentMessage.getOperationType(),
					adjustmentMessage.getSellingPrice());

		} else {
			// Normal message has sale count 1.
			int countOfSale = 1;
			// if count in detail message less than 0 return false without
			// adding
			if (message instanceof DetailedMessage) {
				countOfSale = ((DetailedMessage) message).getInstanceCount();
				if (countOfSale < 0) {
					return false;
				}
			}
			for (int i = 0; i < countOfSale; i++) {
				dao.addSale(new Sale(message.getType(), message.getSellingPrice()));
			}
		}

		return isSaleAdded;
	}

	public Map<String, List<Sale>> getSalesByProductType() {
		List<Sale> totalSales = getSales();
		Map<String, List<Sale>> mapOfProductTypeToSales = new HashMap<>();
		// create map of product types and sales corresponding to each type
		for (Sale sale : totalSales) {
			if (!mapOfProductTypeToSales.containsKey(sale.getProductType())) {
				mapOfProductTypeToSales.computeIfAbsent(sale.getProductType(), s -> new ArrayList<Sale>(1));
			}
			mapOfProductTypeToSales.get(sale.getProductType()).add(sale);
		}
		return mapOfProductTypeToSales;
	}

	// Adjust sales for adjustment message
	private boolean adjustSale(List<Sale> sales, OperationType operationType, BigDecimal sellingPrice) {
		boolean isValidAdjustment = true;
		switch (operationType) {
		case ADD:
			sales.stream().forEach(sale -> sale.setPricePerUnit(sale.getPricePerUnit().add(sellingPrice)));
			break;
		case MULTIPLY:
			sales.stream().forEach(sale -> sale.setPricePerUnit(sale.getPricePerUnit().multiply(sellingPrice)));
			break;
		case SUBTRACT:
			sales.stream().forEach(sale -> sale.setPricePerUnit(sale.getPricePerUnit().subtract(sellingPrice)));
			break; 
		default:
			isValidAdjustment = false;
			System.out.println("Unsupported operation encountered during processing.");
			break;
		}
		return isValidAdjustment;
	}

}
