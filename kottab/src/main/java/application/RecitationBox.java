package application;

import java.util.Arrays;

import ansarcontrols.AnsarHBox;
import ansarcontrols.AnsarLabel;
import ansarcontrols.AnsarLabeledControlHBox;
import ansarcontrols.AnsarVBox;
import ansarcontrols.ControlType;

public class RecitationBox extends AnsarVBox {
	private AnsarLabel title;
	private AnsarLabeledControlHBox fromSurah;
	private AnsarLabeledControlHBox toSurah;
	private AnsarLabeledControlHBox fromAya;
	private AnsarLabeledControlHBox toAya;

	public RecitationBox(String title) {
		this.title = new AnsarLabel(title);
		fromSurah = new AnsarLabeledControlHBox("fromSurah", ControlType.TextField);
		fromAya = new AnsarLabeledControlHBox("fromAya", ControlType.TextField);
		toSurah = new AnsarLabeledControlHBox("toSurah", ControlType.TextField);
		toAya = new AnsarLabeledControlHBox("toAya", ControlType.TextField);
		getChildren().addAll(Arrays.asList(new AnsarHBox(this.title), new AnsarHBox(fromSurah, fromAya),
				new AnsarHBox(toSurah, toAya)));
	}

	public AnsarLabeledControlHBox getFromSurah() {
		return fromSurah;
	}

	public void setFromSurah(AnsarLabeledControlHBox fromSurah) {
		this.fromSurah = fromSurah;
	}

	public AnsarLabeledControlHBox getToSurah() {
		return toSurah;
	}

	public void setToSurah(AnsarLabeledControlHBox toSurah) {
		this.toSurah = toSurah;
	}

	public AnsarLabeledControlHBox getFromAya() {
		return fromAya;
	}

	public void setFromAya(AnsarLabeledControlHBox fromAya) {
		this.fromAya = fromAya;
	}

	public AnsarLabeledControlHBox getToAya() {
		return toAya;
	}

	public void setToAya(AnsarLabeledControlHBox toAya) {
		this.toAya = toAya;
	}
}
