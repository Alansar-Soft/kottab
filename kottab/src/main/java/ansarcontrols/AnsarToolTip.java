package ansarcontrols;

import javafx.scene.control.Tooltip;

public class AnsarToolTip extends Tooltip implements IAnsarNode {
	public AnsarToolTip(String message) {
		setText(message);
	}

	@Override
	public void reset() {
		setText("");
	}

}
