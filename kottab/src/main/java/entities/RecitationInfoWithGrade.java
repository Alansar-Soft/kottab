package entities;

import javax.persistence.Embeddable;

@Embeddable
public class RecitationInfoWithGrade extends AbsRecitationInfo {
	private Byte grade;

	public Byte getGrade() {
		return grade;
	}

	public void setGrade(Byte grade) {
		this.grade = grade;
	}

}
