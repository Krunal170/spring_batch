package com.example.chapter7.reader;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.mapping.PatternMatchingCompositeLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import com.example.chapter7.domain.CustomerWithTran;
import com.example.chapter7.mapper.TransactionFieldSetMapper;
import com.example.chapter7.reader.custom.CustomerFileReader;
import com.example.chapter7.util.DailyJobTimestamper;

@EnableBatchProcessing
@SpringBootApplication
public class NestedFormatJob{

	
//	@Autowired
//	private JobBuilderFactory jobBuilderFactory;
//
//	@Autowired
//	private StepBuilderFactory stepBuilderFactory;
//	
//	//Reader
//	@Bean
//	@StepScope
//	public FlatFileItemReader customerItemReader(
//			@Value("#{jobParameters['customerFile']}")Resource inputFile) {
//
//		return new FlatFileItemReaderBuilder()
//				.name("customerItemReader")
//				.lineMapper(lineTokenizer())
//				.resource(inputFile)
//				.build();
//	}
//	
//	@Bean
//	public CustomerFileReader customerFileReader() {
//		return new CustomerFileReader(customerItemReader(null));
//	}
//	//Combining Tokenizer 
//	@Bean
//	public PatternMatchingCompositeLineMapper lineTokenizer() {
//		//Map created to store multiple tokenizers
//		Map<String,LineTokenizer> lineTokenizers = new HashMap<>(2);
//		
//		//1st line Tokenizer
//		lineTokenizers.put("CUST*",customerLineTokenizer());
//		//2nd line Tokenizer
//		lineTokenizers.put("TRANS*",transactionLineTokenizer());
//		
//		//Map created for FieldSetMapper
//		Map<String,FieldSetMapper> fieldSetMappers = new HashMap<>(2);
//		
//		BeanWrapperFieldSetMapper<CustomerWithTran> customerFieldSetMapper = new BeanWrapperFieldSetMapper<>();
//		customerFieldSetMapper.setTargetType(CustomerWithTran.class);
//		
//		fieldSetMappers.put("CUST*",customerFieldSetMapper);
//		fieldSetMappers.put("TRANS*",new TransactionFieldSetMapper());
//		
//		PatternMatchingCompositeLineMapper lineMappers = new PatternMatchingCompositeLineMapper();
//		lineMappers.setTokenizers(lineTokenizers);
//		lineMappers.setFieldSetMappers(fieldSetMappers);
//		
//		return lineMappers;
//	}
//	
//	@Bean
//	public DelimitedLineTokenizer transactionLineTokenizer() {
//		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
//		lineTokenizer.setNames("prefix","accountNumber","transactionDate","amount");
//		return lineTokenizer;
//	}
//	
//	@Bean
//	public DelimitedLineTokenizer customerLineTokenizer() {
//		DelimitedLineTokenizer lineTokenizer =
//				new DelimitedLineTokenizer();
//
//		lineTokenizer.setNames(
//				"prefix",
//				"firstName",
//				"middleInitial",
//				"lastName",
//				"address",
//				"city",
//				"state",
//				"zipCode");
//
//		//lineTokenizer.setIncludedFields(1, 2, 3, 4, 5, 6, 7,8);
//
//		return lineTokenizer;
//	}
//	
//	@Bean
//	public ItemWriter itemWriter() {
//		return (items) -> items.forEach(System.out::println);
//	}
//
//	@Bean
//	public Step nestedFormatCopyFileStep() {
//		return this.stepBuilderFactory.get("nestedFormatCopyFileStep")
//				.<CustomerWithTran, CustomerWithTran>chunk(10)
//				.reader(customerItemReader(null))
//				.writer(itemWriter())
//				.build();
//	}
//
//	@Bean
//	public Job nestedFormatCopyFileJob() {
//		return this.jobBuilderFactory.get("nestedFormatCopyFileJob")
//				.start(nestedFormatCopyFileStep())
//				.incrementer(new DailyJobTimestamper())
//				.build();
//	}
//
//
//	public static void main(String[] args) {
//		List<String> realArgs = Collections.singletonList("customerFile=/input/csv/customerMultiFormat1.csv");
//
//		SpringApplication.run(NestedFormatJob.class, realArgs.toArray(new String[1]));
//	}
}
