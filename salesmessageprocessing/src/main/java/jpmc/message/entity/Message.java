package jpmc.message.entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "class")
@JsonSubTypes({
        @JsonSubTypes.Type(value = DetailedMessage.class),
        @JsonSubTypes.Type(value = AdjustmentMessage.class)
})
public class Message {
    private String type;
    
    private BigDecimal sellingPrice;

    public Message() {
    }

    public Message(String type, BigDecimal sellingPrice) {
        this.type = type;
        this.sellingPrice = sellingPrice;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }
}
