
package com.brunozambiazi.lunchoice.config;

import com.brunozambiazi.lunchoice.scheduler.TodayChoiceWinnerScheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;


@Configuration
@EnableWebMvc @EnableScheduling
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	
	@Bean
	public TodayChoiceWinnerScheduler todayChoiceWinnerScheduler() {
		return new TodayChoiceWinnerScheduler();
	}
	
	@Bean
	public SpringTemplateEngine templateEngine(TemplateResolver templateResolver) {
		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);
		return templateEngine;
	}
	
	@Bean
	public TemplateResolver templateResolver() {
		TemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setPrefix("views/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode("HTML5");
		return templateResolver;
	}
	
	@Bean
	public ViewResolver viewResolver(SpringTemplateEngine templateEngine) {
		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
		viewResolver.setTemplateEngine(templateEngine);
		return viewResolver;
	}
	
}
