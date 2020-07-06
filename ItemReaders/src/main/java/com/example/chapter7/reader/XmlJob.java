//package com.example.chapter7.reader;
//
//import java.util.Collections;
//import java.util.List;
//
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepScope;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.batch.item.xml.StaxEventItemReader;
//import org.springframework.batch.item.xml.builder.StaxEventItemReaderBuilder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
//import org.springframework.core.io.Resource;
//import org.springframework.oxm.jaxb.Jaxb2Marshaller;
//
//import com.example.chapter7.domain.CustomerXML;
//import com.example.chapter7.domain.Transaction;
//import com.example.chapter7.util.DailyJobTimestamper;
//
//@EnableBatchProcessing
//@SpringBootApplication
//public class XmlJob {
//
//	@Autowired
//	private JobBuilderFactory jobBuilderFactory;
//	
//	@Autowired
//	private StepBuilderFactory stepBuilderFactory;
//	
//	@Bean
//	@StepScope
//	public StaxEventItemReader<CustomerXML> customFileReader(@Value("#{jobParameters['customerFile']}") Resource inputFile){
//		return new StaxEventItemReaderBuilder<CustomerXML>()
//				.name("customFileReader")
//				.resource(inputFile)
//				.addFragmentRootElements("customer")
//				.unmarshaller(customerMarshaller())
//				.build();
//				
//
//	}
//	
//	@Bean
//	public ItemWriter itemWriter() {
//		return (items) -> items.forEach(System.out::println);
//	}
//	
//	@Bean
//	public Jaxb2Marshaller customerMarshaller() {
//		Jaxb2Marshaller jaxb2Marshaller = new Jaxb2Marshaller();
//		jaxb2Marshaller.setClassesToBeBound(CustomerXML.class,Transaction.class);
//		return jaxb2Marshaller;
//	}
//	
//	
//	
//	@Bean
//	public Step copyXmlFileStep() {
//		return this.stepBuilderFactory.get("copyXmlFileStep")
//				.<CustomerXML,CustomerXML>chunk(10)
//				.reader(customFileReader(null))
//				.writer(itemWriter())
//				.build();
//	}
//	
//	@Bean
//	public Job copyXmljob() {
//		return this.jobBuilderFactory.get("copyXmljob")
//				.start(copyXmlFileStep())
//				.incrementer(new DailyJobTimestamper())
//				.build();
//	}
//
//
//	public static void main(String[] args) {
//		List<String> realArgs = Collections.singletonList("customerFile=/input/xml/customer.xml");
//
//		SpringApplication.run(XmlJob.class, realArgs.toArray(new String[1]));
//	}
//}
