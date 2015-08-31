
package com.brunozambiazi.lunchoice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;


@Configuration
public class EntityManagerFactoryTest {

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setPersistenceXmlLocation("META-INF/persistence-test.xml");
		return entityManagerFactory;
	}
	
}
