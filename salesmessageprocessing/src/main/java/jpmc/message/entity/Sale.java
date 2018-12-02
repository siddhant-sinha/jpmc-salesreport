package jpmc.message.entity;

import java.math.BigDecimal;

/**
 * Created by Siddhant Sinha on 12/01/2018.
 */
public class Sale {

	private String productType;

	private BigDecimal pricePerUnit;
	

	public Sale(String productType, BigDecimal pricePerUnit) {
		this.productType=productType;
		this.pricePerUnit=pricePerUnit;
	}

	public String getProductType() {
		return productType;
	}

	public BigDecimal getPricePerUnit() {
		return pricePerUnit;
	}
	
	public void setPricePerUnit(BigDecimal pricePerUnit) {
		this.pricePerUnit = pricePerUnit;
	}


}
