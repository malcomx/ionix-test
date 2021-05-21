package cl.ionix.test.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(value = { "cl.ionix.test" })

@SpringBootApplication
public class IonixWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(IonixWebApplication.class, args);
	}
}
