package br.uninter.medalerta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MedalertaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedalertaApplication.class, args);
	}

}
