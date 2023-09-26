package ansarcontrols;

import javafx.scene.control.TextArea;

public class AnsarTextArea extends TextArea implements IAnsarControl<String> {
	public AnsarTextArea() {
		setPrefWidth(520);
		setPrefHeight(50);
	}

	@Override
	public String fetchValue() {
		return getText();
	}

	@Override
	public void insertValue(String val) {
		setText(val);
	}

	@Override
	public void reset() {
		clear();
	}

}
