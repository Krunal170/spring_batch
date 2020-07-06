package com.example.chapter7;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.mapping.PassThroughFieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.batch.item.file.transform.PatternMatchingCompositeLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.StringUtils;

import com.example.chapter7.mapper.CustomerFieldSetMapper;
import com.example.chapter7.orderreader.Order;
import com.example.chapter7.orderreader.internal.OrderItemReader;
import com.example.chapter7.orderreader.mapper.AddressFieldSetMapper;
import com.example.chapter7.orderreader.mapper.BillingFieldSetMapper;
import com.example.chapter7.orderreader.mapper.HeaderFieldSetMapper;
import com.example.chapter7.orderreader.mapper.OrderItemFieldSetMapper;
import com.example.chapter7.orderreader.mapper.ShippingFieldSetMapper;
import com.example.chapter7.util.DailyJobTimestamper;

@EnableBatchProcessing
@SpringBootApplication(scanBasePackages= "com.example")
public class ItemReadersApplication {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	
	@Autowired
	JobLauncher jobLauncher;
	
	@Value("${datafile.file}")
	private String filePath;
	//Reader
	
	//Item Writer to console
	@Bean
	@StepScope
	public ItemWriter<Order> itemWriter17(){
		return (items) -> items.forEach(System.out::println);
	}
	
	
	//Tokenizer
	@Bean
	public PatternMatchingCompositeLineTokenizer orderFileTokenizer() throws Exception
	{

		
		DelimitedLineTokenizer headerRecordTokenizer = new DelimitedLineTokenizer();
		headerRecordTokenizer.setDelimiter(";");
		headerRecordTokenizer.setStrict(false);
		headerRecordTokenizer.setNames("LINE_ID","ORDER_ID","ORDER_DATE");
		headerRecordTokenizer.afterPropertiesSet();
		
		
		
		DelimitedLineTokenizer footerRecordTokenizer = new DelimitedLineTokenizer();
		footerRecordTokenizer.setDelimiter(";");
		footerRecordTokenizer.setStrict(false);
		footerRecordTokenizer.setNames("LINE_ID","TOTAL_LINE_ITEMS","TOTAL_ITEMS","TOTAL_PRICE");
		footerRecordTokenizer.afterPropertiesSet();

		DelimitedLineTokenizer businessCustomerLineTokenizer = new DelimitedLineTokenizer();
		businessCustomerLineTokenizer.setDelimiter(";");
		businessCustomerLineTokenizer.setStrict(false);
		businessCustomerLineTokenizer.setNames("LINE_ID","COMPANY_NAME","REG_ID","VIP");
		businessCustomerLineTokenizer.afterPropertiesSet();
		
		

		DelimitedLineTokenizer customerLineTokenizer = new DelimitedLineTokenizer();
		customerLineTokenizer.setDelimiter(";");
		customerLineTokenizer.setStrict(false);
		customerLineTokenizer.setNames("LINE_ID","LAST_NAME","FIRST_NAME","MIDDLE_NAME","REGISTERED","REG_ID","VIP");
		customerLineTokenizer.afterPropertiesSet();
		

		DelimitedLineTokenizer billingAddressLineTokenizer = new DelimitedLineTokenizer();
		billingAddressLineTokenizer.setDelimiter(";");
		billingAddressLineTokenizer.setStrict(false);
		billingAddressLineTokenizer.setNames("LINE_ID","ADDRESSEE","ADDR_LINE1","ADDR_LINE2","CITY","ZIP_CODE","STATE","COUNTRY");
		billingAddressLineTokenizer.afterPropertiesSet();
		

		DelimitedLineTokenizer shippingAddressLineTokenizer = new DelimitedLineTokenizer();
		shippingAddressLineTokenizer.setDelimiter(";");
		shippingAddressLineTokenizer.setStrict(false);
		shippingAddressLineTokenizer.setNames("LINE_ID","ADDRESSEE","ADDR_LINE1","ADDR_LINE2","CITY","ZIP_CODE","STATE","COUNTRY");
		shippingAddressLineTokenizer.afterPropertiesSet();
		

		DelimitedLineTokenizer billingLineTokenizer = new DelimitedLineTokenizer();
        billingAddressLineTokenizer.setDelimiter(";");
        billingLineTokenizer.setStrict(false);
		billingLineTokenizer.setNames("LINE_ID","PAYMENT_TYPE_ID","PAYMENT_DESC");
		billingLineTokenizer.afterPropertiesSet();
		

		DelimitedLineTokenizer shippingLineTokenizer = new DelimitedLineTokenizer();
        shippingAddressLineTokenizer.setDelimiter(";");
        shippingLineTokenizer.setStrict(false);
		shippingLineTokenizer.setNames("LINE_ID","SHIPPER_ID","SHIPPING_TYPE_ID","ADDITIONAL_SHIPPING_INFO");
		shippingLineTokenizer.afterPropertiesSet();
		

		DelimitedLineTokenizer itemLineTokenizer = new DelimitedLineTokenizer();
		itemLineTokenizer.setDelimiter(";");
		itemLineTokenizer.setStrict(false);
		itemLineTokenizer.setNames("LINE_ID","ITEM_ID","PRICE","DISCOUNT_PERC","DISCOUNT_AMOUNT","SHIPPING_PRICE","HANDLING_PRICE","QUANTITY","TOTAL_PRICE");
		itemLineTokenizer.afterPropertiesSet();
		

		DelimitedLineTokenizer defaultLineTokenizer = new DelimitedLineTokenizer();
		defaultLineTokenizer.setDelimiter(";");
		defaultLineTokenizer.setStrict(false);
		defaultLineTokenizer.afterPropertiesSet();
		
		Map<String,LineTokenizer> lineTokenizers = new HashMap<>(9);
		PatternMatchingCompositeLineTokenizer lineTokenizer = new PatternMatchingCompositeLineTokenizer();
		lineTokenizers.put("HEA*", headerRecordTokenizer);
		lineTokenizers.put("FOT*", footerRecordTokenizer);
		lineTokenizers.put("BCU*", businessCustomerLineTokenizer);
		lineTokenizers.put("NCU*", customerLineTokenizer);
		lineTokenizers.put("BAD*", billingAddressLineTokenizer);
		lineTokenizers.put("SAD*", shippingAddressLineTokenizer);
		lineTokenizers.put("BIN*", billingLineTokenizer);
		lineTokenizers.put("SIN*", shippingLineTokenizer);
		lineTokenizers.put("LIT*", itemLineTokenizer);
		lineTokenizers.put("*", defaultLineTokenizer);
		
		//Below code writen for reading number of Record Types provided in input file
				
		lineTokenizer.setTokenizers(lineTokenizers);
		
		return lineTokenizer;
	}
	
	   
		
	
	   
        @StepScope
	    @Bean
		public FlatFileItemReader fileItemReader() throws Exception {
	    	
	    	 DefaultLineMapper lineMapper = new DefaultLineMapper<>();
			 FieldSetMapper fieldMapper = new PassThroughFieldSetMapper();
			 
			 lineMapper.setLineTokenizer(orderFileTokenizer());
			 lineMapper.setFieldSetMapper(fieldMapper);
			 
			return 
				  new FlatFileItemReaderBuilder<>()
				  										.name("fileItemReader")
				  										.resource(new FileSystemResource(filePath))
				  										 .lineMapper(lineMapper)
	     		  										.build();
				  										
		} 
	    
	  
	    
	@Bean
	public OrderItemReader reader() throws Exception{
		OrderItemReader orderItemReader = new OrderItemReader();
		
						orderItemReader.setFieldSetReader(fileItemReader());
						
						orderItemReader.setHeaderMapper(new HeaderFieldSetMapper());
						orderItemReader.setCustomerMapper(new com.example.chapter7.orderreader.mapper.CustomerFieldSetMapper());
						orderItemReader.setAddressMapper(new AddressFieldSetMapper());
						orderItemReader.setBillingMapper(new BillingFieldSetMapper());
						orderItemReader.setItemMapper(new OrderItemFieldSetMapper());
						orderItemReader.setShippingMapper(new ShippingFieldSetMapper());	
						
			return 	orderItemReader;			
	}
	
	@Bean
	public ItemWriter itemWriter() {
		return (items) -> items.forEach(System.out::println);
	}
	
	//Create step 
		@Bean
		public Step copyFilStep() throws Exception {
			return  ((SimpleStepBuilder<Order, Order>) this.stepBuilderFactory.get("copyFilStep")
					.<Order,Order>chunk(20)
					.reader(reader())
					.stream(fileItemReader()))
					.writer(itemWriter17())
					.build();
		}

	//Create Job
	@Bean
	public Job fixDelimatedFileReadJob() throws Exception {
		return this.jobBuilderFactory.get("fixDelimatedFileReadJob")
				.start(copyFilStep())
				.incrementer(new DailyJobTimestamper())
				.build();
	}
	
	public static void main(String[] args) {
	    List<String> realArgs = Arrays.asList("input/fixsizefile/customerFixedWidth.txt");
	    SpringApplication.run(ItemReadersApplication.class, realArgs.toArray(new String[1]));
        
   }
}
