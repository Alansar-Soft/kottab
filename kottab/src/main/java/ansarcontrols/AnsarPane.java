package ansarcontrols;

import java.util.List;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class AnsarPane extends Pane implements IAnsarParent {
	public AnsarPane() {
		getStyleClass().add("pane");
	}

	@Override
	public List<? extends Node> fetchChildren() {
		return getChildren();
	}

}
