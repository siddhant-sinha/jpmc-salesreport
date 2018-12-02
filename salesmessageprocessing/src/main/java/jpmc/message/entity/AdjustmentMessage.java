package jpmc.message.entity;

import java.math.BigDecimal;

public class AdjustmentMessage extends Message {
    OperationType operationType;

    public AdjustmentMessage() {}

    public AdjustmentMessage(OperationType operationType) {
        this.operationType = operationType;
    }

    public AdjustmentMessage(String type, BigDecimal sellingPrice, OperationType operationType) {
        super(type, sellingPrice);
        this.operationType = operationType;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }
}
