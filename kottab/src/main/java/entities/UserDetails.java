package entities;

import javax.persistence.Embeddable;

import org.hibernate.annotations.Nationalized;

@Embeddable
public class UserDetails {
	private String personType;
	private Long personCode;

	@Nationalized
	public String getPersonType() {
		return personType;
	}

	public void setPersonType(String personType) {
		this.personType = personType;
	}

	public Long getPersonCode() {
		return personCode;
	}

	public void setPersonCode(Long personCode) {
		this.personCode = personCode;
	}

}
