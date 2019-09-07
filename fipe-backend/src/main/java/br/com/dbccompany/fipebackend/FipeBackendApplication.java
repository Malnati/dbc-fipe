package br.com.dbccompany.fipebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.client.RestTemplate;

import java.util.Locale;

@SpringBootApplication
@EnableMongoRepositories
public class FipeBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(FipeBackendApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public Locale defaultLocale() {
		return new Locale("pt", "BR");
	}

}
