package com.example.springbatchexample.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//@Entity
//@Table(name = "BATCH_JOB_EXECUTION")
public class H2Report {
	
//	@Id
//	@Column(name = "JOB_EXECUTION_ID")
	private Long jobExecutionId;
	
//	@Column(name = "END_TIME")
	private LocalDateTime endTime;
	
//	@Column(name = "EXIT_CODE")
	private String exitCode;

	public H2Report() {
		
	}

	public H2Report(Long jobExecutionId, LocalDateTime endTime, String exitCode) {
		this.jobExecutionId = jobExecutionId;
		this.endTime = endTime;
		this.exitCode = exitCode;
	}

	public Long getJobExecutionId() {
		return jobExecutionId;
	}

	public void setJobExecutionId(Long jobExecutionId) {
		this.jobExecutionId = jobExecutionId;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public String getExitCode() {
		return exitCode;
	}

	public void setExitCode(String exitCode) {
		this.exitCode = exitCode;
	}

}
