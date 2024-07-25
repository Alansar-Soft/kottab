package ansarcontrols;

import javafx.scene.control.CheckBox;
import utilities.ObjectChecker;

public class AnsarCheckBox extends CheckBox implements IAnsarControl<Boolean> {
	public AnsarCheckBox()
	{
		getStyleClass().add("btn");
	}

	@Override
	public Boolean fetchValue() {
		return ObjectChecker.toFalseIfNull(isSelected());
	}

	@Override
	public void insertValue(Boolean val) {
		setSelected(ObjectChecker.toFalseIfNull(val));
	}

	@Override
	public void reset() {
		setSelected(false);
	}

}
