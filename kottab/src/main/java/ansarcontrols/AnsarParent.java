package ansarcontrols;

import java.util.List;

import javafx.scene.Node;
import javafx.scene.Parent;

public class AnsarParent extends Parent implements IAnsarParent {

	@Override
	public List<? extends Node> fetchChildren() {
		return getChildren();
	}
}
