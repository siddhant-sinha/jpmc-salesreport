package jpmc.message.entity;

import java.math.BigDecimal;

public class DetailedMessage extends Message {
    private int instanceCount;

    public DetailedMessage() {}

    public DetailedMessage(int instanceCount) {
        this.instanceCount = instanceCount;
    }

    public DetailedMessage(String type, BigDecimal sellingPrice, int instanceCount) {
        super(type, sellingPrice);
        this.instanceCount = instanceCount;
    }

    public int getInstanceCount() {
        return instanceCount;
    }

    public void setInstanceCount(int instanceCount) {
        this.instanceCount = instanceCount;
    }
}
