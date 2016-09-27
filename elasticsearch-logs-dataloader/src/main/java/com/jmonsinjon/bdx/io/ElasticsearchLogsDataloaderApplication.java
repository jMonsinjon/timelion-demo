package com.jmonsinjon.bdx.io;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ElasticsearchLogsDataloaderApplication {

	public static void main(String[] args) {

		SpringApplication.run(ElasticsearchLogsDataloaderApplication.class, args);

		while(true){
			System.console().printf("toto");
		}
	}
}
