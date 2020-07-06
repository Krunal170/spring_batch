package com.example.chapter7.reader.custom;

import java.util.ArrayList;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.file.ResourceAwareItemReaderItemStream;
import org.springframework.core.io.Resource;

import com.example.chapter7.domain.CustomerWithTran;
import com.example.chapter7.domain.Transaction;

public class CustomerFileReader implements ResourceAwareItemReaderItemStream<CustomerWithTran>{

	private Object custItem = null;
	
	private ResourceAwareItemReaderItemStream<Object> delegate;
	
	public CustomerFileReader(ResourceAwareItemReaderItemStream<Object> delegate) {
		this.delegate=delegate;
	}
	
	public CustomerWithTran read() throws Exception{
		if(custItem == null) {
			custItem = delegate.read();
		}
		
		CustomerWithTran item = (CustomerWithTran)custItem;
		custItem = null;
		
		if(item != null) {
			
			item.setTransactions(new ArrayList<>());
			
			while(peek() instanceof Transaction) {
				item.getTransactions().add((Transaction) custItem);
				custItem = null;
			}
		}
		return item;
	}
	
	private Object peek() throws Exception {
		if (custItem == null) {
			custItem = delegate.read();
		}
		return custItem;
	}

	public void close() throws ItemStreamException {
		delegate.close();
	}

	public void open(ExecutionContext arg0) throws ItemStreamException {
		delegate.open(arg0);
	}

	public void update(ExecutionContext arg0) throws ItemStreamException {
		delegate.update(arg0);
	}

	@Override
	public void setResource(Resource resource) {
		this.delegate.setResource(resource);
	}
}
