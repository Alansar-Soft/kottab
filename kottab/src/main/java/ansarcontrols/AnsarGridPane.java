package ansarcontrols;

import java.util.List;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;

public class AnsarGridPane extends GridPane implements IAnsarParent {
	@Override
	public List<? extends Node> fetchChildren() {
		return getChildren();
	}
}
