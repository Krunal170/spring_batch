//package com.example.chapter7.reader;
//
//import java.text.ParseException;
//import java.util.HashMap;
//import java.util.Map;
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
//import org.springframework.batch.item.database.JdbcPagingItemReader;
//import org.springframework.batch.item.database.PagingQueryProvider;
//import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
//import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
//
//import com.example.chapter7.domain.CustomerDBDAO;
//import com.example.chapter7.mapper.CustomerRowMapper;
//import com.example.chapter7.util.CustomerItemListener;
//import com.example.chapter7.util.DailyJobTimestamper;
//import com.example.chapter7.util.EmptyInputStepFailer;
//
//@EnableBatchProcessing
//@SpringBootApplication
//public class JdbcPagingJob {
//
//	@Autowired
//	private JobBuilderFactory jobBuilderFactory;
//	
//	@Autowired
//	private StepBuilderFactory stepBuilderFactory;
//	
//	@Bean
//	@StepScope
//	public JdbcPagingItemReader<CustomerDBDAO> customerIteamReader( DataSource dataSource,
//																	PagingQueryProvider queryProvider,
//																	@Value("#{jobParameters['city']}") String city){
//		Map<String, Object> parameterValues = new HashMap<>(1);
//		parameterValues.put("city", city);
//		
//		return new JdbcPagingItemReaderBuilder<CustomerDBDAO>()
//				   .name("customerIteamReader")
//				   .dataSource(dataSource)
//				   .queryProvider(queryProvider)
//				   .parameterValues(parameterValues)
//				   .pageSize(20)
//				   .rowMapper(new CustomerRowMapper())
//				   .build();
//		}
//	
//	@Bean
//	public SqlPagingQueryProviderFactoryBean pagingQueryProvider(DataSource dataSource) {
//		SqlPagingQueryProviderFactoryBean factoryBean = new SqlPagingQueryProviderFactoryBean();
//		
//		factoryBean.setDataSource(dataSource);
//		factoryBean.setSelectClause("SELECT *");
//		factoryBean.setFromClause("FROM CUSTOMER");
//		factoryBean.setWhereClause("WHERE city = :city");
//		factoryBean.setSortKey("LASTNAME");
//		
//		return factoryBean;
//	}
//	
//	@Bean
//	public ItemWriter<CustomerDBDAO> itemWriter() {
//		return (items) -> items.forEach(System.out::println);
//	}
//
//	@Bean
//	public CustomerItemListener customerListner() {
//		return new CustomerItemListener();
//	}
//	
//	@Bean
//	public EmptyInputStepFailer emptyFileFailer() {
//		return new EmptyInputStepFailer();
//	}
//	@Bean
//	public Step jdbcPagingCopyFileStep() {
//		return this.stepBuilderFactory.get("jdbcPagingCopyFileStep")
//				.<CustomerDBDAO, CustomerDBDAO>chunk(20)
//				.reader(customerIteamReader(null, null, null))
//				.faultTolerant()
//				.skip(ParseException.class)
//				.skipLimit(10)
//				.writer(itemWriter())
//				.listener(customerListner())   //Listen for Parse Exception an log it
//				.listener(emptyFileFailer())   //Check if empty file is passed
// 				.build();
//	}
//
//	@Bean
//	public Job jdbcPagingCopyFileJob() {
//		return this.jobBuilderFactory.get("jdbcPagingCopyFileJob")
//				.start(jdbcPagingCopyFileStep())
//				.incrementer(new DailyJobTimestamper())
//				.build();
//	}
//
//
//	public static void main(String[] args) {
//
//		SpringApplication.run(JdbcPagingJob.class, "city=Chicago");
//	}
//}
