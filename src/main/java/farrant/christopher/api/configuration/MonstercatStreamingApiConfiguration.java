package farrant.christopher.api.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import farrant.christopher.api.MonstercatStreamingApiApplication;

@Configuration
public class MonstercatStreamingApiConfiguration {
	
	@Bean
	public Logger logger() {
		return LoggerFactory.getLogger(MonstercatStreamingApiApplication.class);
	}
	
	public void adjustLoggingLevel(ch.qos.logback.classic.Level newLevel) {
		ch.qos.logback.classic.Logger l =
				(ch.qos.logback.classic.Logger)logger();
		l.setLevel(newLevel);
	}
}
