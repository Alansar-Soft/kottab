package ansarcontrols;

import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class AnsarVBox extends VBox implements IAnsarParent {

	public AnsarVBox() {
	}

	public AnsarVBox(Node... nodes) {
		setPadding(new Insets(5));
		setSpacing(5);
		setNodeOrientation(NodeOrientation.INHERIT);
		getChildren().addAll(nodes);
	}

	@Override
	public List<? extends Node> fetchChildren() {
		return getChildren();
	}
}
