package application;

import ansarcontrols.AnsarLabeledControlHBox;
import ansarcontrols.ControlType;
import entities.AbsRecitationInfo;
import entities.RecitationInfoWithGrade;
import utilities.ObjectChecker;

public class RecitationBoxWithGrade extends RecitationBox {
	private AnsarLabeledControlHBox<String> grade;

	public RecitationBoxWithGrade(String title) {
		super(title);
		grade = new AnsarLabeledControlHBox<>("grade", ControlType.TextField);
		getChildren().add(grade);
	}

	public byte getGrade() {
		return Byte.valueOf(ObjectChecker.isEmptyOrZeroOrNull(grade.fetchValue()) ? "0" : grade.fetchValue());
	}

	public void setGrade(byte grade) {
		this.grade.insertValue(grade);
	}

	@Override
	public AbsRecitationInfo fetchRecitationInfo() {
		RecitationInfoWithGrade info = new RecitationInfoWithGrade();
		info.setFromSurah(getFromSurah());
		info.setFromAya(getFromAya());
		info.setToSurah(getToSurah());
		info.setToAya(getToAya());
		info.setGrade(ObjectChecker.isEmptyOrZeroOrNull(getGrade()) ? Byte.valueOf("0") : Byte.valueOf(getGrade()));
		return info;
	}

}
