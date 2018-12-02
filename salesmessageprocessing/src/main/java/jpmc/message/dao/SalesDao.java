package jpmc.message.dao;

import java.util.List;

import jpmc.message.entity.Sale;

/**
 * Created by Siddhant Sinha on 12/01/2018.
 */
public interface SalesDao {

	/**
	 * @param sale
	 * add sale to records.
	 */
    void addSale(Sale sale);

    /**
     * return list of sales
     */
    List<Sale> getSales();

}
