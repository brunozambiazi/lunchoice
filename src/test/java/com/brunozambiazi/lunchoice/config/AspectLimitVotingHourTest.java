
package com.brunozambiazi.lunchoice.config;

import com.brunozambiazi.lunchoice.backend.service.VoteService;
import java.util.Calendar;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;


/**
 * Este aspecto foi criado apenas para facilitar os testes unitários.
 *   Seu único objetivo é interceptar as chamadas ao método {@link VoteService#getLimitHourForVote()} para retornar datas 
 *     conhecidas e esperadas pelos testes, evitando assim possíveis erros causados pelo horário de execução dos testes unitários.
 */
@Aspect
public class AspectLimitVotingHourTest {

	public static Calendar LIMIT;
	
	
	@Around("execution(public * getLimitHourForVote(..))")
	public Object beforeAdvice(ProceedingJoinPoint jp) throws Throwable {
		return LIMIT;
	}
	
}
