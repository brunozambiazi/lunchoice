
package com.brunozambiazi.lunchoice.backend.model;

import com.brunozambiazi.framework.model.BasicEntity;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;


@Entity
public class Vote extends BasicEntity<PersonalDailyVote> {

	private static final long serialVersionUID = 57485256575233868L;

	@EmbeddedId
	@Getter @Setter
	private PersonalDailyVote id;
	
	@ManyToOne
	@Getter @Setter
	private Restaurant restaurant;

}
