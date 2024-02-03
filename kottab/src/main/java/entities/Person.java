package entities;

import java.time.LocalDate;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import utilities.ObjectChecker;
import utilities.ResourceUtility;
import utilities.Result;

@MappedSuperclass
public abstract class Person extends AnsarBaseEntity {
	private LocalDate birthdate;
	private Address address;
	private String firstPhoneNo;
	private String secondPhoneNo;
	private Boolean azharStudent;

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
		if (ObjectChecker.isNotEmptyOrZeroOrNull(address))
			return ResourceUtility.translate(address.getCountry());

		return "";
	}

	@Transient
	public String getCity() {
		if (ObjectChecker.isNotEmptyOrZeroOrNull(address))
			return ResourceUtility.translate(address.getCity());
		return "";
	}

	@Transient
	public String getTown() {
		if (ObjectChecker.isNotEmptyOrZeroOrNull(address))
			return ResourceUtility.translate(address.getTown());
		return "";
	}

	@Transient
	public String getAzharStudentVal() {
		return ObjectChecker.toStringOrEmpty(getAzharStudent());
	}

	@Override
	@Transient
	public Result isValidForCommit() {
		Result result = super.isValidForCommit();
		if (ObjectChecker.isEmptyOrZeroOrNull(birthdate))
			result.accmulate(Result.createFailureResult("You must enter birthdate"));
		validatePhoneNumber(firstPhoneNo, "First", result);
		validatePhoneNumber(secondPhoneNo, "Second", result);
		return result;
	}

	public void validatePhoneNumber(String phoneNum, String messagePrefix, Result result) {
		if (ObjectChecker.isEmptyOrZeroOrNull(phoneNum))
			return;
		if (phoneNum.length() != 11)
			result.accmulate(Result.createFailureResult(messagePrefix + " phone number must be 11 digits exactly"));
		try {
			Long.valueOf(phoneNum);
		} catch (NumberFormatException e) {
			result.accmulate(Result.createFailureResult(messagePrefix + " phone number must have numbers only"));
		}
	}

}
