package mech.mania.MM25JavaStarterPack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

/**
 * A class to run the {@link SpringApplication} server.
 */
@SpringBootApplication
public class Mm25JavaStarterPackApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Mm25JavaStarterPackApplication.class);
		String port = System.getenv("PORT");
		app.setDefaultProperties(Collections
				.singletonMap("server.port", port));
		app.run(args);
	}

}
