package ansarcontrols;

import java.util.List;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class AnsarPane extends Pane implements IAnsarParent {

	@Override
	public List<? extends Node> fetchChildren() {
		return getChildren();
	}

}
