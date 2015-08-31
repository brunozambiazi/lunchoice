
package com.brunozambiazi.lunchoice.backend.service;

import com.brunozambiazi.framework.service.BaseService;
import com.brunozambiazi.lunchoice.backend.model.Restaurant;
import com.brunozambiazi.lunchoice.backend.model.Vote;


public interface RestaurantService extends BaseService<Restaurant, String> {

	/**
	 * Busca, de forma aleatória, qualquer restaurante cadastrado na base. 
	 * 
	 * @return Instância de {@link Restaurant} ou <strong>null</strong>
	 */
	Restaurant findAnyone();
	
	/**
	 * Com base nos votos efetuados hoje, faz a escolha do restaurante mais votado.
	 *   A escolha consiste em atualizar o registro do restaurante, marcando-o como já escolhido na semana atual.
	 * 
	 *   Caso haja empate entre dois ou mais restaurantes, qualquer um poderá retornar.
	 *   Caso não haja votos efetuados hoje, qualquer restaurante da base será retornado.
	 * 
	 * @return Instância de {@link Restaurant} representando o que fora mais votado
	 */
	Restaurant makeChoiceToday();
	
	/**
	 * Recebimento de voto por um restaurante.
	 * 
	 * @param vote {@link Vote} que será computado 
	 */
	void receive(Vote vote);
	
}
