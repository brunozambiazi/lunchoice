
package com.brunozambiazi.lunchoice.backend.service;

import com.brunozambiazi.framework.service.BaseService;
import com.brunozambiazi.lunchoice.backend.model.Person;
import com.brunozambiazi.lunchoice.backend.model.PersonalDailyVote;
import com.brunozambiazi.lunchoice.backend.model.Restaurant;
import com.brunozambiazi.lunchoice.backend.model.Vote;
import java.util.Calendar;
import java.util.Collection;


public interface VoteService extends BaseService<Vote, PersonalDailyVote> {
	
	/**
	 * Busca o voto associado à determinada pessoa em determinado dia.
	 * 
	 * @param person {@link Person} cujo voto do dia será pesquisado 
	 * @param date {@link Calendar} data (sem considerar horário) em que o voto da pessoa será pesquisado
	 * @return Instância de {link Vote} ou <strong>null</strong>
	 */
	Vote findByPersonOn(Person person, Calendar date);
	
	/**
	 * Busca todos os votos existentes na base para o dia de hoje.
	 * 
	 * @return {@link Collection} com todos o votos encontrados
	 */
	Collection<Vote> findToday();
	
	/**
	 * Retorna a data atual com o horário definido como limite para votação.
	 * 
	 * @return Instância de {@link Calendar} representando a data limite (data+hora) da votação 
	 */
	Calendar getLimitHourForVote();
	
	/**
	 * Com base em todos os votos efeutados hoje, retorna o restaurante mais votado.
	 *   Havendo empate no número de votos entre dois ou mais restaurantes, escolhe qualquer um de forma aleatória
	 * 
	 * @return Instância do {@link Restaurant} mais votado, ou <strong>null</strong> caso não haja votos hoje
	 */
	Restaurant mostVotedToday();
	
}
