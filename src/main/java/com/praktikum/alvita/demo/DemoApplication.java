package com.praktikum.alvita.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) {
		Demo.run();
		System.out.println();
		System.out.println("##### PERBANDINGAN KINERJA ARRAY vs ARRAYLIST #####");
		new Comparison().run(new int[]{10, 1_000, 100_000});
	}
}
