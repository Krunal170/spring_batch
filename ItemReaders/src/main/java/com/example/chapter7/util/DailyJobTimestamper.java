package com.example.chapter7.util;

import java.util.Date;
//To make job unique this helper class is added
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersIncrementer;
import org.springframework.stereotype.Component;
@Component
public class DailyJobTimestamper implements JobParametersIncrementer {

	@Override
	public JobParameters getNext(JobParameters parameters) {
		return new JobParametersBuilder(parameters)
				.addDate("currentDate", new Date())
				.toJobParameters();
	}

}
