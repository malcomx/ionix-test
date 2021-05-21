package cl.ionix.test.web;

import java.time.Duration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableAspectJAutoProxy
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {
	"cl.ionix.test.repository"
})
@EntityScan(basePackages = {
	"cl.ionix.test.repository.entity"	
})
public class IonixConfiguration {

	@Bean
	public MessageSource messageSource(){
		ResourceBundleMessageSource message = new ResourceBundleMessageSource();
		message.setBasename("messages/messages");
		message.setDefaultEncoding("UTF-8");
		return message;
	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder
				.setConnectTimeout(Duration.ofMillis(3500))
				.setReadTimeout(Duration.ofMillis(3500))
				.build();
	}
}
