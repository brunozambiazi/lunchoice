
package com.brunozambiazi.lunchoice.backend.model;

import com.brunozambiazi.framework.util.CalendarUtil;
import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;


@Embeddable
public class PersonalDailyVote implements Serializable {

	private static final long serialVersionUID = -8683640865330852447L;

	@Getter @Setter
	private String date;
	
	@ManyToOne
	@Getter @Setter
	private Person person;
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		
		PersonalDailyVote other = (PersonalDailyVote)obj;
		
		if (getDate() == null) {
			if (other.getDate() != null) {
				return false;
			}
		} else if (!getDate().equals(other.getDate())) {
			return false;
		}
		
		if (getPerson() == null) {
			if (other.getPerson() != null) {
				return false;
			}
		} else if (!getPerson().equals(other.getPerson())) {
			return false;
		}
		
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result + ((getDate() == null) ? super.hashCode() : getDate().hashCode()));
		result = (prime * result + ((getPerson() == null) ? super.hashCode() : getPerson().hashCode()));
		return result;
	}
	
	
	public Calendar getDateCalendar() {
		return CalendarUtil.fromString(getDate(), CalendarUtil.DATE_FORMAT);
	}
	public void setDateCalendar(Calendar calendar) {
		setDate(CalendarUtil.toString(calendar, CalendarUtil.DATE_FORMAT));
	}
	
}
