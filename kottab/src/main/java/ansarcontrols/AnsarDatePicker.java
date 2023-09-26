package ansarcontrols;

import java.time.LocalDate;

import javafx.scene.control.DatePicker;

public class AnsarDatePicker extends DatePicker implements IAnsarControl<LocalDate> {
	public AnsarDatePicker() {
		setPrefWidth(200);
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
