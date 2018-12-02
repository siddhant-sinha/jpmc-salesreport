package jpmc.message.service;

import java.util.List;
import java.util.Map;

import jpmc.message.entity.Message;
import jpmc.message.entity.Sale;

/**
 * Created by Siddhant Sinha on 12/01/2018.
 */
public interface SalesService {

	/**
	 *Get sales data from message and add to records
	 */
    boolean addSale(Message message);
    
    /**
	 *Get map of product type to sales
	 */
    Map<String,List<Sale>> getSalesByProductType();

    
}
