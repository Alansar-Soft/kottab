package entities;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

import utilities.ObjectChecker;

@Embeddable
public class RecitationInfoWithGrade extends AbsRecitationInfo {
	private Byte grade;

	public Byte getGrade() {
		return grade;
	}

	public void setGrade(Byte grade) {
		this.grade = grade;
	}

	@Override
	@Transient
	public boolean isEmpty() {
		return super.isEmpty() || ObjectChecker.isEmptyOrZeroOrNull(grade);
	}
}
