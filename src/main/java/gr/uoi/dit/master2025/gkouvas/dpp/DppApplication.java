package gr.uoi.dit.master2025.gkouvas.dpp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

	@EnableScheduling
	@SpringBootApplication
	@EnableConfigurationProperties
	public class DppApplication {
		public static void main(String[] args) {
			SpringApplication.run(DppApplication.class, args);
		}
	}



