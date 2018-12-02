package jpmc.message.dao;

import java.util.ArrayList;
import java.util.List;

import jpmc.message.entity.Sale;

/**
 * Created by Siddhant Sinha on 12/01/2018.
 */
public class SalesDaoImpl implements SalesDao {

    private static List<Sale> sales=new ArrayList<Sale>(1);

    public void addSale(Sale sale) {

        sales.add(sale);
    }

    public List<Sale> getSales() {
        return sales;
    }
}
