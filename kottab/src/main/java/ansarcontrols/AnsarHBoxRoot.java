package ansarcontrols;

import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.layout.HBox;

public class AnsarHBoxRoot extends HBox {
	public AnsarHBoxRoot() {
		setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
		setSpacing(5);
		setPadding(new Insets(5));
	}
}
