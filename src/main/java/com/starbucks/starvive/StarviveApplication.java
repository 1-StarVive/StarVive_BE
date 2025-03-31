package com.starbucks.starvive;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.starbucks.starvive")
public class StarviveApplication {

	public static void main(String[] args) {

		Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
		String dbUsername = dotenv.get("DB_USERNAME");
		String dbPassword = dotenv.get("DB_PASSWORD");

		if (dbUsername != null) {
    		System.setProperty("DB_USERNAME", dbUsername);
		}
		if (dbPassword != null) {
    	System.setProperty("DB_PASSWORD", dbPassword);
		}

		SpringApplication.run(StarviveApplication.class, args);
	}

}
