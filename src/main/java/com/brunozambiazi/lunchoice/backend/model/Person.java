
package com.brunozambiazi.lunchoice.backend.model;

import com.brunozambiazi.framework.model.BasicEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;


@Entity
public class Person extends BasicEntity<String> {

	private static final long serialVersionUID = 3732061366283241263L;

	@Id
	@Column(name = "id")
	@Getter @Setter
	private String email;
	
	@Getter @Setter
	private String name;

	
	@Override
	public String getId() {
		return getEmail();
	}
	@Override
	public void setId(String id) {
		setEmail(id);
	}
	
}
