//package com.example.chapter7.reader;
////Reader for Fix Length Size File
//import java.util.Arrays;
//import java.util.List;
//
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepScope;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.batch.item.file.FlatFileItemReader;
//import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
//import org.springframework.batch.item.file.transform.Range;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
//import org.springframework.core.io.Resource;
//
//import com.example.chapter7.domain.Customer;
//
//@EnableBatchProcessing
//@SpringBootApplication(scanBasePackages= "com.example.chapter7")
//public class FixWidthJob {
//
//	@Autowired
//	private JobBuilderFactory jobBuilderFactory;
//
//	@Autowired
//	private StepBuilderFactory stepBuilderFactory;
//	
//	@Autowired
//	JobLauncher jobLauncher;
//	
//	//Step scoped Fix Length File reader
//	@Bean
//	@StepScope
//	public FlatFileItemReader<Customer> customerItemReader(
//			//Input Parameter from Job Parameters
//			@Value("#{jobParameters['customerFile']}") Resource inputFile) {
//			
//		return new FlatFileItemReaderBuilder<Customer>()
//				.name("customerItemReader")   //Name of Builder
//				.resource(inputFile)		  //Passing input file to Builder
//				.fixedLength()				  //Tell Buider this is fix legth file
//				.columns(
//						new Range[]{
//										new Range(1,11),
//										new Range(12, 12), 
//										new Range(13, 22),
//										new Range(23, 26),
//										new Range(27,46),
//										new Range(47,62),
//										new Range(63,64),
//										new Range(65,69)
//									})
//				        .names(new String[] {
//				        				"firstName",
//				        				"middleInitial",
//				        				"lastName",
//				        				"addressNumber",
//				        				"street", 
//				        				"city", 
//				        				"state",
//				        				"zipCode"
//				        			})
//				.targetType(Customer.class)
//				.build();
//	}
//	
//	//Item Writer to console
//	@StepScope
//	public ItemWriter<Customer> itemWriter(){
//		return (items) -> items.forEach(System.out::println);
//	}
//	
//	//Create step 
//	@Bean
//	public Step copyFilStep() {
//		System.out.println("Inside step : copyFilStep");
//		return this.stepBuilderFactory.get("copyFilStep")
//				.<Customer,Customer>chunk(10)
//				.reader(customerItemReader(null))
//				.writer(itemWriter())
//				.build();
//	}
//	
//	//Create Job
//	@Bean
//	public Job fixDelimatedFileReadJob() {
//		System.out.println("Inside job: fixDelimatedFileReadJob......");
//		return this.jobBuilderFactory.get("fixDelimatedFileReadJob")
//				.start(copyFilStep())
//				.build();
//	}
//	
//	public static void main(String[] args) {
//		System.out.println("Inside main...........");
//		List<String> realArgs = Arrays.asList("customerFile=/input/fixsizefile/customerFixedWidth.txt");
//	    SpringApplication.run(FixWidthJob.class, realArgs.toArray(new String[1]));
//
//   }
//}
