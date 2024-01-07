package ansarcontrols;

import java.time.LocalDate;

import javafx.scene.control.DatePicker;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class AnsarDatePicker extends DatePicker implements IAnsarControl<LocalDate> {
	public AnsarDatePicker() {
		setPrefWidth(200);
		getEditor().setFont(Font.font("Times New Roman", FontWeight.BLACK, 14));
		setValue(LocalDate.now());
	}

	@Override
	public LocalDate fetchValue() {
		return getValue();
	}

	@Override
	public void insertValue(LocalDate val) {
		setValue(val);
	}

	@Override
	public void reset() {
		setValue(LocalDate.now());
	}
}
