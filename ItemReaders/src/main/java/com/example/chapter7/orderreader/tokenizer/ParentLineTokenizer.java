package com.example.chapter7.orderreader.tokenizer;

import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;

public abstract class ParentLineTokenizer extends DelimitedLineTokenizer {

	@Override
	public void setDelimiter(String delimiter) {
		super.setDelimiter(";");
	}
	
}
