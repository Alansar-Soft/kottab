package entities.files;

import javax.persistence.Embeddable;

import org.hibernate.annotations.Nationalized;

@Embeddable
public class Address {
	private String country;
	private String city;
	private String town;

	@Nationalized
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Nationalized
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Nationalized
	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}
}
