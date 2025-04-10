package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StudyLogApplication {

	public static void main(String[] args) {
		
//		DotEnv dotenv = DotEnv.load(); // または configure()...load()

        // 確認したい環境変数名を指定
//        String databaseUrl = System.getProperty("DATABASE_URL");
//        System.out.println("DATABASE_URL from System properties: " + databaseUrl);
        
		SpringApplication.run(StudyLogApplication.class, args);
	}

}
