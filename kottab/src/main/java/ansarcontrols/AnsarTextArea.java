package ansarcontrols;

import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class AnsarTextArea extends TextArea implements IAnsarControl<String> {
	public AnsarTextArea() {
		setPrefWidth(520);
		setPrefHeight(50);
		setFont(Font.font("Times New Roman", FontWeight.BLACK, 14));
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
