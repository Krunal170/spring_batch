package com.example.chapter7.orderreader.tokenizer;

public class HeaderRecordTokenizer extends ParentLineTokenizer {

	@Override
	public void setNames(String... names) {
		super.setNames("LINE_ID","ORDER_ID","ORDER_DATE");
	}
}
