package application;

import java.util.Arrays;
import java.util.function.Consumer;

import ansarcontrols.AnsarHBox;
import ansarcontrols.AnsarLabel;
import ansarcontrols.AnsarLabeledControlHBox;
import ansarcontrols.AnsarTextField;
import ansarcontrols.AnsarVBox;
import ansarcontrols.ControlType;
import entities.AbsRecitationInfo;
import entities.RecitationInfo;
import utilities.ObjectChecker;
import utilities.ResourceUtility;
import utilities.Surah;

public class RecitationBox extends AnsarVBox {
	private AnsarLabel title;
	private AnsarLabeledControlHBox<String> fromSurah;
	private AnsarLabeledControlHBox<String> toSurah;
	private AnsarLabeledControlHBox<String> fromAya;
	private AnsarLabeledControlHBox<String> toAya;

	public RecitationBox(String title) {
		this.title = new AnsarLabel(title);
		fromSurah = new AnsarLabeledControlHBox<>("fromSurah", ControlType.TextField);
		((AnsarTextField) fromSurah.getControl()).setEditable(false);
		fromAya = new AnsarLabeledControlHBox<>("fromAya", ControlType.TextField);
		((AnsarTextField) fromAya.getControl()).setEditable(false);
		toSurah = new AnsarLabeledControlHBox<>("toSurah", ControlType.TextField);
		toAya = new AnsarLabeledControlHBox<>("toAya", ControlType.TextField);
		getChildren().addAll(Arrays.asList(new AnsarHBox(this.title), new AnsarHBox(fromSurah, fromAya),
				new AnsarHBox(toSurah, toAya)));
	}

	public Surah getFromSurah() {
		return ResourceUtility.fetchSurah(fromSurah.fetchValue());
	}

	public void setFromSurah(String fromSurah) {
		this.fromSurah.insertValue(fromSurah);
	}

	public Surah getToSurah() {
		return ResourceUtility.fetchSurah(toSurah.fetchValue());
	}

	public void setToSurah(String toSurah) {
		this.toSurah.insertValue(toSurah);
	}

	public Short getFromAya() {
		return Short.valueOf(fromAya.fetchValue());
	}

	public void setFromAya(Short fromAya) {
		this.fromAya.insertValue(fromAya);
	}

	public Short getToAya() {
		return Short.valueOf(toAya.fetchValue());
	}

	public void setToAya(Short toAya) {
		this.toAya.insertValue(toAya);
	}

	public void toAyaCallback(Consumer<Short> callback) {
		((AnsarTextField) toAya.getControl()).setOnAction(e -> callback.accept(getToAya()));
	}

	public boolean isEmpty() {
		return ObjectChecker.areAllEmptyOrNull(getFromSurah(), getFromAya(), getToSurah(), getToAya());
	}

	public AbsRecitationInfo fetchRecitationInfo() {
		RecitationInfo recitation = new RecitationInfo();
		recitation.setFromSurah(getFromSurah());
		recitation.setFromAya(getFromAya());
		recitation.setToSurah(getToSurah());
		recitation.setToAya(getToAya());
		return recitation;
	}
}
