package com.deltaServices.logindemo.logindemo;

import com.deltaServices.logindemo.logindemo.User.User;
import com.deltaServices.logindemo.logindemo.User.UserRepository;
import com.deltaServices.logindemo.logindemo.User.UserService;
import com.deltaServices.logindemo.logindemo.User.userStatus;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LogindemoApplication {

	public static void main(String[] args) {

		User adminUser = new User(
				"admin@loginDemo.com",
				"admin",
				"admin",
				"admin!",
				userStatus.Admin,
				false,
				false,
				true
		);


		SpringApplication.run(LogindemoApplication.class, args);
	}

}
