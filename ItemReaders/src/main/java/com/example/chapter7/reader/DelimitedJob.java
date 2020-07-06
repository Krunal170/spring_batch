//package com.example.chapter7.reader;
//
//import java.util.Arrays;
//import java.util.List;
//
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepScope;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.batch.item.file.FlatFileItemReader;
//import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
//import org.springframework.core.io.Resource;
//
//import com.example.chapter7.domain.Customer;
//import com.example.chapter7.domain.CustomerCSV;
//import com.example.chapter7.mapper.CustomerFieldSetMapper;
//import com.example.chapter7.mapper.CustomerFileLineTokenizer;
//import com.example.chapter7.util.DailyJobTimestamper;
//
//@EnableBatchProcessing
//@SpringBootApplication
//public class DelimitedJob {
//
//	@Autowired
//	private JobBuilderFactory jobBuilderFactory;
//
//	@Autowired
//	private StepBuilderFactory stepBuilderFactory;
//
//	@Bean
//	@StepScope
//	public FlatFileItemReader<CustomerCSV> customerCSVItemReader(@Value("#{jobParameters['customerFile']}")Resource inputFile) {
//		return new FlatFileItemReaderBuilder<CustomerCSV>()
//				.name("customerItemReader")
//				.delimited()
//				.names(new String[] {"firstName",
//						"middleInitial",
//						"lastName",
//						"addressNumber",
//						"street",
//						"city",
//						"state",
//						"zipCode"})
//				.lineTokenizer(new CustomerFileLineTokenizer())
//				.fieldSetMapper(new CustomerFieldSetMapper())
//				.resource(inputFile)
//				.build();
//	}
//	
//
//	@Bean
//	public ItemWriter<CustomerCSV> itemCSVWriter() {
//		return (items) -> items.forEach(System.out::println);
//	}
//
//	@Bean
//	public Step readCSVFileStep1() {
//		return this.stepBuilderFactory.get("readCSVFileStep1")
//				.<CustomerCSV, CustomerCSV>chunk(10)
//				.reader(customerCSVItemReader(null))
//				.writer(itemCSVWriter())
//				.build();
//	}
//
//	@Bean
//	public Job readCSVFileStepJob1() {
//		return this.jobBuilderFactory.get("readCSVFileStepJob1")
//				.start(readCSVFileStep1())
//				.incrementer(new DailyJobTimestamper())
//				.build();
//	}
//
//
//	public static void main(String[] args) {
//		List<String> realArgs = Arrays.asList("customerFile=/input/csv/customer.csv");
//		SpringApplication.run(DelimitedJob.class, realArgs.toArray(new String[1]));
//	}
//	
//	
//
//}