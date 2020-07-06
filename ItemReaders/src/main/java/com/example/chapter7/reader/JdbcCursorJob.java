//package com.example.chapter7.reader;
//
//import javax.sql.DataSource;
//
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepScope;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.batch.item.database.JdbcCursorItemReader;
//import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
//import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
//
//import com.example.chapter7.domain.CustomerDBDAO;
//import com.example.chapter7.mapper.CustomerRowMapper;
//import com.example.chapter7.util.DailyJobTimestamper;
//
//@EnableBatchProcessing
//@SpringBootApplication
//public class JdbcCursorJob {
//	
//	@Autowired 
//	private JobBuilderFactory jobBuilderFactory;
//	
//	@Autowired
//	private StepBuilderFactory stepBuilderFactory;
//	
//	@Bean
//	public JdbcCursorItemReader<CustomerDBDAO> customerItemReader(DataSource dataSource){
//		return new JdbcCursorItemReaderBuilder<CustomerDBDAO>()
//				   .name("customerItemReader")
//				   .dataSource(dataSource)
//				   .sql("select * from customer where city = ?")
//				   .rowMapper(new CustomerRowMapper())
//				   .preparedStatementSetter(citySetter(null))
//				   .build();
//	}
//	
//	@Bean
//	@StepScope
//	public ArgumentPreparedStatementSetter citySetter(
//			@Value("#{jobParameters['city']}") String city) {
//		return new ArgumentPreparedStatementSetter(new Object [] {city});
//	}
//	
//	@Bean
//	public ItemWriter<CustomerDBDAO> itemWriter() {
//		return (items) -> items.forEach(System.out::println);
//	}
//	
//	@Bean
//	public Step jdbcFileStep() {
//		return this.stepBuilderFactory.get("jdbcFileStep")
//				.<CustomerDBDAO, CustomerDBDAO>chunk(10)
//				.reader(customerItemReader(null))
//				.writer(itemWriter())
//				.build();
//	}
//
//	@Bean
//	public Job JdbcJob() {
//		return this.jobBuilderFactory.get("JdbcJob")
//				.start(jdbcFileStep())
//				.incrementer(new DailyJobTimestamper())
//				.build();
//	}
//
//
//	public static void main(String[] args) {
//
//		SpringApplication.run(JdbcCursorJob.class, "city=Helena");
//	}
//
//}
