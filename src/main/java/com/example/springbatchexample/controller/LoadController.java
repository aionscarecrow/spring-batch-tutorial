package com.example.springbatchexample.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.springbatchexample.model.H2Report;
//import com.example.springbatchexample.repository.H2ReportRepository;

@RestController
@RequestMapping
public class LoadController {
	
	@Autowired
	JobLauncher jobLauncher;
	
	@Autowired
	Job job;
	
//	@Autowired
//	H2ReportRepository repository;
	
	@GetMapping("/load")
	public BatchStatus load() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
		
		Map<String, JobParameter> maps = new HashMap<>();
		maps.put("time", new JobParameter(System.currentTimeMillis()));
		JobParameters parameters = new JobParameters(maps);
		JobExecution jobExecution = jobLauncher.run(job, parameters);
		
		System.out.println("JobExecution status: " + jobExecution.getStatus());
		
		System.out.print("Batch is running...");
		while(jobExecution.isRunning()) {
			System.out.print(".");
		}
		
		return jobExecution.getStatus();
	}
	
//	@GetMapping("/report")
//	public List<H2Report> report() {
//		return repository.findAll();
//	}

}
