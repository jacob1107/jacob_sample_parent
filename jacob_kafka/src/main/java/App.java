

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.availability.ApplicationAvailability;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class App {
	public static void main(String[] args) {
		/*
		 * System.setProperty("KUBERNETES_SERVICE_HOST", "10.96.0.1");
		 * System.setProperty("KUBERNETES_SERVICE_PORT", "443");
		 */
		System.setProperty("SERVICE_HOST", "10.96.0.1");
		System.setProperty("SERVICE_PORT", "443");
		SpringApplication.run(App.class, args);
	}
}
