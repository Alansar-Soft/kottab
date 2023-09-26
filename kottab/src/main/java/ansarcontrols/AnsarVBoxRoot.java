package ansarcontrols;

import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;

public class AnsarVBoxRoot extends AnsarVBox {
	public AnsarVBoxRoot() {
		setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
		setSpacing(5);
		setPadding(new Insets(5));
	}
}
