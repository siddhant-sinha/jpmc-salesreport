package jpmc.message.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jpmc.message.entity.AdjustmentMessage;
import jpmc.message.entity.Message;
import jpmc.message.entity.Sale;
import jpmc.message.service.SalesService;
import jpmc.message.service.SalesServiceImpl;

/**
 * Created by Siddhant Sinha on 12/01/2018.
 */
public class SalesMessageController {

	SalesService service = null;
	private static int MESSAGE_PROCESSING_CAPACITY = 50;

	public SalesMessageController() {
		service = new SalesServiceImpl();
	}

	public static void main(String[] args) throws ParseException {
		SalesMessageController controller = new SalesMessageController();
		List<Message> messages = null;
		ObjectMapper mapper = new ObjectMapper();

		try {
			//read file and load messages
			messages = mapper.readValue(new File(controller.getClass().getClassLoader().getResource("messages.json").getFile()), new TypeReference<List<Message>>() {
			});
			//process messages
			controller.process(messages);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void process(List<Message> messages) {
		int processedMessages = 0;
		StringBuilder adjustmentsLog = new StringBuilder();

		for (Message message : messages) {
			//step1: add sale to records for each message received. If not added continue with others.
			boolean isSaleAdded=service.addSale(message);
			if(!isSaleAdded){
				continue;
			}
			processedMessages++;
			
			//step2: if adjustment recorded then create Adjustment log
			createAdjustmentMessageLog(adjustmentsLog, message);

			//step3: if processed message reached 10 print in console records.
			if (processedMessages % 10 == 0) {
				System.out.println("\n*** Sales Record Details For Products ***");
				Map<String, List<Sale>> salesByType=service.getSalesByProductType();
				for(Map.Entry<String, List<Sale>> record : salesByType.entrySet()) {					
		            System.out.println("Product type: " + record.getKey() +", Total units sold: " + record.getValue().size() +
		                    ", Revenue generated: " + getRevenueForProduct(record.getValue())
		            );
		        }
			}

			//step4: check and stop if processing reached maximum(50)
			if (processedMessages == MESSAGE_PROCESSING_CAPACITY) {
				System.out.println("\nDue to limited processing capacity, only a total of "
						+ MESSAGE_PROCESSING_CAPACITY + " messages are processed. Stopped Message Processing.");
				break;
			}
		}

		//step5: print all adjustment messages.
		if (adjustmentsLog.length() != 0) {
			System.out.println("\n*** Adjustment Log *** \n" + adjustmentsLog.toString());
		}

	}

	private void createAdjustmentMessageLog(StringBuilder adjustmentsLog, Message message) {
		if (message instanceof AdjustmentMessage) {
			adjustmentsLog.append("Product (");
			adjustmentsLog.append(message.getType());
			adjustmentsLog.append(") is adjusted (operation: ");
			adjustmentsLog.append(((AdjustmentMessage) message).getOperationType());
			adjustmentsLog.append(") by a value of ");
			adjustmentsLog.append(message.getSellingPrice());
			adjustmentsLog.append(" at approximately ");
			adjustmentsLog.append(new Date());
			adjustmentsLog.append(".\n");
		}
	}
	
	 private BigDecimal getRevenueForProduct(List<Sale> sales) { 
	        BigDecimal revenueGenerated = BigDecimal.ZERO;

	        for(Sale sale : sales) {
	            revenueGenerated = revenueGenerated.add(sale.getPricePerUnit());
	        }

	        return revenueGenerated;
	    }
}
