package ansarcontrols;

import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import utilities.ObjectChecker;

public class AnsarTextField extends TextField implements IAnsarControl<Object> {
	public AnsarTextField() {
		setPrefWidth(200);
		setMinHeight(30);
		setFont(Font.font("Times New Roman", FontWeight.BLACK, 14));
	}

	@Override
	public String fetchValue() {
		return getText();
	}

	@Override
	public void insertValue(Object val) {
		setText(ObjectChecker.toString(val));
	}

	@Override
	public void reset() {
		clear();
	}

}
