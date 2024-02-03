package ansarcontrols;

import javafx.scene.control.PasswordField;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class AnsarPasswordField extends PasswordField implements IAnsarControl<String> {

	public AnsarPasswordField() {
		setPrefWidth(200);
		setMinHeight(30);
		setFont(Font.font("Times New Roman", FontWeight.BLACK, 14));
	}

	@Override
	public void reset() {
		clear();
	}

	@Override
	public String fetchValue() {
		return getText();
	}

	@Override
	public void insertValue(String val) {
		setText(val);
	}

}
