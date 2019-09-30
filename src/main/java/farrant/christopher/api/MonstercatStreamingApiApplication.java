package farrant.christopher.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
@EnableAutoConfiguration
public class MonstercatStreamingApiApplication extends SpringBootServletInitializer {
	
	public static void main(String[] args) {
		SpringApplication.run(MonstercatStreamingApiApplication.class, args);
	}
}
