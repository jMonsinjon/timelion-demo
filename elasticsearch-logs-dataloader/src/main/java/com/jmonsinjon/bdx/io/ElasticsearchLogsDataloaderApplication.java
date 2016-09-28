package com.jmonsinjon.bdx.io;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration
public class ElasticsearchLogsDataloaderApplication {

	public static void main(String[] args) {

		SpringApplication.run(ElasticsearchLogsDataloaderApplication.class, args);
	}
}
