
package com.brunozambiazi.lunchoice.backend.service;

import com.brunozambiazi.framework.service.BaseService;
import com.brunozambiazi.lunchoice.backend.model.Person;
import com.brunozambiazi.lunchoice.backend.model.Restaurant;


public interface PersonService extends BaseService<Person, String> {

	/**
	 * Verifica se a votação é permitida no horário atual.
	 * 
	 * @return <strong>true</strong> quando for permitida
	 */
	boolean canVoteNow();
	
	/**
	 * Verifica se determinada pessoa ainda pode votar hoje.
	 * 
	 * @param person {@link Person} sobre a qual a verificação será feita
	 * @return <strong>true</strong> quando a pessoa ainda puder votar
	 */
	boolean canVoteToday(Person person);
	
	/**
	 * Voto de determinada pessoa em determinado restaurante.
	 * 
	 * @param person {@link Person} que efetuará o voto
	 * @param restaurant {@link Restaurant} que receberá o voto
	 */
	void vote(Person person, Restaurant restaurant);
	
}
