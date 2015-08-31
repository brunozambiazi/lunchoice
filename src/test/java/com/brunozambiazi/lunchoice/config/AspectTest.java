
package com.brunozambiazi.lunchoice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@Configuration
@EnableAspectJAutoProxy
public class AspectTest {

	@Bean
	public AspectLimitVotingHourTest vagabunda() {
		return new AspectLimitVotingHourTest();
	}
	
}
