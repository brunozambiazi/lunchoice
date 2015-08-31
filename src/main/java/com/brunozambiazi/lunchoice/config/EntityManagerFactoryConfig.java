
package com.brunozambiazi.lunchoice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;


@Configuration
public class EntityManagerFactoryConfig {

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		return new LocalContainerEntityManagerFactoryBean();
	}
	
}
