package com.example.springbatchexample.batch;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.springbatchexample.model.User;
import com.example.springbatchexample.repository.UserRepository;

@Component
public class DBWriter implements ItemWriter<User> {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void write(List <? extends User> users) throws Exception {
		System.out.println("Data saved for Users: " + users);
		userRepository.saveAll(users);
	}

}
