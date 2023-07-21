package ssubob.ssubob;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SsubobApplication {

	public static void main(String[] args) {
		SpringApplication.run(SsubobApplication.class, args);
	}

}
