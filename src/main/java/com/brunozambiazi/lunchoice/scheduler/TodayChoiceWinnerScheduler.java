
package com.brunozambiazi.lunchoice.scheduler;

import com.brunozambiazi.framework.spring.Message;
import com.brunozambiazi.lunchoice.backend.model.Person;
import com.brunozambiazi.lunchoice.backend.model.Restaurant;
import com.brunozambiazi.lunchoice.backend.service.PersonService;
import com.brunozambiazi.lunchoice.backend.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;


public class TodayChoiceWinnerScheduler {

	@Autowired
	private PersonService personService;
	
	@Autowired
	private RestaurantService restaurantService;
	
	@Autowired
	private Message message;

	@Autowired
	private JavaMailSender mail;
	
	
	@Scheduled(cron = "${cron.choice.scheduler}")
	public void run() {
		Restaurant restaurant = restaurantService.makeChoiceToday();
		
		if (restaurant == null) {
			return;
		}
		
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setSubject(message.get("mail.subject"));
		mailMessage.setText(message.get("mail.body", restaurant.getName()));
		
		for (Person person : personService.findAll()) {
			mailMessage.setTo(person.getEmail());
			mail.send(mailMessage);
		}
	}
	
}
