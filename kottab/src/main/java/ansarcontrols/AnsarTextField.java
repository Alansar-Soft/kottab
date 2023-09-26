package ansarcontrols;

import javafx.scene.control.TextField;
import utilities.ObjectChecker;

public class AnsarTextField extends TextField implements IAnsarControl<Object> {
	public AnsarTextField() {
		setPrefWidth(200);
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
