
package com.brunozambiazi.lunchoice.config;

import com.brunozambiazi.framework.spring.Message;
import java.util.Properties;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@ComponentScan(basePackages = "com.brunozambiazi.lunchoice", excludeFilters = {@Filter(Configuration.class)})
@Configuration
@EnableScheduling @EnableTransactionManagement
public class ApplicationConfig {

	@Bean
	public JavaMailSender mailSender() {
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost("smtp.gmail.com");
		sender.setPort(587);
		sender.setUsername("lunchoiceapp@gmail.com");
		sender.setPassword("lunchoice300815");

		Properties properties = sender.getJavaMailProperties();
		properties.put("mail.transport.protocol", "smtp");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		
		return sender;
	}
	
	@Bean
	public Message messageSource() {
		Message message = new Message();
		message.setBasename("messages");
		return message;
	}
	
	@Bean
    public PropertyPlaceholderConfigurer properties() {
        PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
        ppc.setLocation(new ClassPathResource("application.properties"));
        ppc.setIgnoreUnresolvablePlaceholders(true);
        return ppc;
    }
	
	@Bean
	public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactoryBean) {
		JpaTransactionManager manager = new JpaTransactionManager();
		manager.setEntityManagerFactory(entityManagerFactoryBean.getObject());
		return manager;
	}
	
}
