package com.paj.psd2.aggregator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class BankingDataAggregatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankingDataAggregatorApplication.class, args);
	}
}
