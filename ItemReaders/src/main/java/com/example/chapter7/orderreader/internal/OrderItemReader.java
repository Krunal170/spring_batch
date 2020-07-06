package com.example.chapter7.orderreader.internal;

import java.util.ArrayList;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.stereotype.Component;
import com.example.chapter7.orderreader.Customer;
import com.example.chapter7.orderreader.Address;
import com.example.chapter7.orderreader.BillingInfo;
import com.example.chapter7.orderreader.LineItem;
import com.example.chapter7.orderreader.Order;
import com.example.chapter7.orderreader.ShippingInfo;
import com.sun.istack.Nullable;
@Component
public class OrderItemReader implements ItemReader<Order>{

	private Order order;
	
	private boolean recordFinished;
	
	//Mapper
	private FieldSetMapper<Order> headerMapper;
	private FieldSetMapper<Customer> customerMapper;
	private FieldSetMapper<Address> addressMapper;
	private FieldSetMapper<BillingInfo> billingMapper;
	private FieldSetMapper<LineItem> itemMapper;
	private FieldSetMapper<ShippingInfo> shippingMapper;

	private ItemReader<FieldSet> fieldSetReader;
		
	
	@Nullable
	@Override
	public Order read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		recordFinished = false;
		
		while(!recordFinished) {
			process(fieldSetReader.read());
		}

		Order result = order;
		order = null;

		return result;
	}
	
	private void process(FieldSet fieldSet) throws Exception{
		
		//1. finish processing if we hit the end of file
		if (fieldSet == null) {
				recordFinished = true;
				order = null;
				return;
		 }
				
        //2. Read first Column from data(RecordId)
		String lineId = fieldSet.readString(0);
		
		//3.Checking for header 
		if (Order.LINE_ID_HEADER.equals(lineId)) {
			order = headerMapper.mapFieldSet(fieldSet);
		}		
		else if (Order.LINE_ID_FOOTER.equals(lineId)) {
			// Do mapping for footer here, because mapper does not allow to pass
			// an Order object as input.
			// Mapper always creates new object
			order.setTotalPrice(fieldSet.readBigDecimal("TOTAL_PRICE"));
			order.setTotalLines(fieldSet.readInt("TOTAL_LINE_ITEMS"));
			order.setTotalItems(fieldSet.readInt("TOTAL_ITEMS"));

			// mark we are finished with current Order
			recordFinished = true;
		}
		else if (Customer.LINE_ID_BUSINESS_CUST.equals(lineId)) {
			//log.debug("MAPPING CUSTOMER");
			if (order.getCustomer() == null) {
				Customer customer = customerMapper.mapFieldSet(fieldSet);
				customer.setBusinessCustomer(true);
				order.setCustomer(customer);
			}
		}
		else if (Customer.LINE_ID_NON_BUSINESS_CUST.equals(lineId)) {
			//log.debug("MAPPING CUSTOMER");
			if (order.getCustomer() == null) {
				Customer customer = customerMapper.mapFieldSet(fieldSet);
				customer.setBusinessCustomer(false);
				order.setCustomer(customer);
			}
		}
		else if (Address.LINE_ID_BILLING_ADDR.equals(lineId)) {
			//log.debug("MAPPING BILLING ADDRESS");
			order.setBillingAddress(addressMapper.mapFieldSet(fieldSet));
		}
		else if (Address.LINE_ID_SHIPPING_ADDR.equals(lineId)) {
			//log.debug("MAPPING SHIPPING ADDRESS");
			order.setShippingAddress(addressMapper.mapFieldSet(fieldSet));
		}
		else if (BillingInfo.LINE_ID_BILLING_INFO.equals(lineId)) {
			//log.debug("MAPPING BILLING INFO");
			order.setBilling(billingMapper.mapFieldSet(fieldSet));
		}
		else if (ShippingInfo.LINE_ID_SHIPPING_INFO.equals(lineId)) {
			//log.debug("MAPPING SHIPPING INFO");
			order.setShipping(shippingMapper.mapFieldSet(fieldSet));
		}
		else if (LineItem.LINE_ID_ITEM.equals(lineId)) {
			//log.debug("MAPPING LINE ITEM");
			if (order.getLineItems() == null) {
				order.setLineItems(new ArrayList<>());
			}
			order.getLineItems().add(itemMapper.mapFieldSet(fieldSet));
		}
		else {
			//log.debug("Could not map LINE_ID=" + lineId);
		}
	}

	/**
	 * @param flatFileItemReader reads lines from the file converting them to
	 *        {@link FieldSet}.
	 */
	
	public void setFieldSetReader(ItemReader<FieldSet> fieldSetReader) {
		this.fieldSetReader = fieldSetReader;
	}

	public void setAddressMapper(FieldSetMapper<Address> addressMapper) {
		this.addressMapper = addressMapper;
	}

	public void setBillingMapper(FieldSetMapper<BillingInfo> billingMapper) {
		this.billingMapper = billingMapper;
	}

	public void setCustomerMapper(FieldSetMapper<Customer> customerMapper) {
		this.customerMapper = customerMapper;
	}

	public void setHeaderMapper(FieldSetMapper<Order> headerMapper) {
		this.headerMapper = headerMapper;
	}

	public void setItemMapper(FieldSetMapper<LineItem> itemMapper) {
		this.itemMapper = itemMapper;
	}

	public void setShippingMapper(FieldSetMapper<ShippingInfo> shippingMapper) {
		this.shippingMapper = shippingMapper;
	}


  
}


