
package com.brunozambiazi.lunchoice.backend.model;

import com.brunozambiazi.framework.model.BasicEntity;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.hibernate.annotations.GenericGenerator;


@Entity
public class Restaurant extends BasicEntity<String> implements Comparable<Restaurant> {

	private static final long serialVersionUID = 4104832210711532343L;
	
	@Id
    @GeneratedValue(generator = "uuid") @GenericGenerator(name = "uuid", strategy = "uuid2")
	@Getter @Setter
	private String id;
	
	@Getter @Setter
	private String name;
	
	@OneToMany(mappedBy = "restaurant", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@Getter @Setter
	private Set<Vote> votes;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@Getter @Setter
	private Set<String> chosenWeeks;
	
	@Transient
	@Getter @Setter
	private Integer votesToday;
	
	
	@Override
	public int compareTo(Restaurant other) {
		return new CompareToBuilder()
		    .append(other.getVotesToday(), getVotesToday())
			.append(getName(), other.getName())
			.append(getId(), other.getId())
			.toComparison();
	}
	
}
