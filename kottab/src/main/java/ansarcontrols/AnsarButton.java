package ansarcontrols;

import javafx.scene.control.Button;
import utilities.ResourceUtility;

public class AnsarButton extends Button {

	public AnsarButton(String text) {
		super(ResourceUtility.id(text));
		setId("ansarBtn");
	}

}
