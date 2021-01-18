package com.example.springbatchexample.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.Resource;

import com.example.springbatchexample.model.User;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {
	
	@Bean
	@Lazy
	public Job job(
			JobBuilderFactory jobFactory,
			StepBuilderFactory stepFactory, 
			ItemReader<User> itemReader,
			ItemProcessor<User, User> itemProcessor,
			ItemWriter<User> itemWriter) {
		
		Step step = stepFactory.get("ETL-files-load")
				.<User, User> chunk(100)
				.reader(itemReader)
				.processor(itemProcessor)
				.writer(itemWriter)
				.build();
		
		return jobFactory.get("ETL-Load")
			.incrementer(new RunIdIncrementer())
			.start(step) // if multiple steps then .flow(step).next(step)...
			.build();
	}
	
	@Bean
	public FlatFileItemReader<User> itemReader(@Value("${input}") Resource resource) {
		FlatFileItemReader<User> flatFileItemReader = new FlatFileItemReader<>();
		flatFileItemReader.setResource(resource);
		flatFileItemReader.setName("CSV-Reader");
		flatFileItemReader.setLinesToSkip(1);
		flatFileItemReader.setLineMapper(lineMapper());
		return flatFileItemReader;
	}
	
	@Bean
	public LineMapper<User> lineMapper() {
		DefaultLineMapper<User> defaultLineMapper = new DefaultLineMapper<>();
		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
		
		lineTokenizer.setDelimiter(",");
		lineTokenizer.setStrict(false);
		lineTokenizer.setNames("id", "name", "dept", "salary");
		
		BeanWrapperFieldSetMapper<User> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(User.class);
		
		defaultLineMapper.setLineTokenizer(lineTokenizer);
		defaultLineMapper.setFieldSetMapper(fieldSetMapper);
		
		return defaultLineMapper;
	}
}
