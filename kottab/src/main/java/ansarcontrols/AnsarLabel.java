package ansarcontrols;

import javafx.scene.control.Label;
import utilities.ResourceUtility;

public class AnsarLabel extends Label implements IAnsarControl {

	public AnsarLabel(String id) {
		super(ResourceUtility.id(id));
		setPrefWidth(200);
	}

	public AnsarLabel() {
		this("");
	}

	@Override
	public String fetchValue() {
		return getText();
	}

	@Override
	public void insertValue(Object val) {
		setText(val.toString());
	}

	@Override
	public void reset() {
		setText("");
	}

}
