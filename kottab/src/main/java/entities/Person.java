package entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import utilities.ObjectChecker;

@MappedSuperclass
public abstract class Person extends AnsarBaseEntity {
	private LocalDateTime registrationDate;
	private LocalDate birthdate;
	private Address address;
	private String firstPhoneNo;
	private String secondPhoneNo;
	private Boolean azharStudent;

	public LocalDateTime getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(LocalDateTime registrationDate) {
		this.registrationDate = registrationDate;
	}

	public LocalDate getBirthdate() {	
		return birthdate;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Boolean getAzharStudent() {
		return azharStudent;
	}

	public void setAzharStudent(Boolean azharStudent) {
		this.azharStudent = azharStudent;
	}

	public String getFirstPhoneNo() {
		return firstPhoneNo;
	}

	public void setFirstPhoneNo(String firstPhoneNo) {
		this.firstPhoneNo = firstPhoneNo;
	}

	public String getSecondPhoneNo() {
		return secondPhoneNo;
	}

	public void setSecondPhoneNo(String secondPhoneNo) {
		this.secondPhoneNo = secondPhoneNo;
	}

	@Transient
	public String getCountry() {
		if (ObjectChecker.isNotEmptyOrNull(address))
			return ObjectChecker.toString(address.getCountry());

		return "";
	}

	@Transient
	public String getCity() {
		if (ObjectChecker.isNotEmptyOrNull(address))
			return ObjectChecker.toString(address.getCity());
		return "";
	}

	@Transient
	public String getTown() {
		if (ObjectChecker.isNotEmptyOrNull(address))
			return ObjectChecker.toString(address.getTown());
		return "";
	}

	@Transient
	public String getAzharStudentVal() {
		return ObjectChecker.toString(getAzharStudent());
	}
}
