package ansarcontrols;

import javafx.geometry.NodeOrientation;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class AnsarScene extends Scene implements IAnsarNode {

	public AnsarScene(Parent root) {
		this(root, -1, -1);
	}

	public AnsarScene(Parent root, double width, double height) {
		super(root, width, height);
		getStylesheets().add("application.css");
		setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
	}

	@Override
	public void reset() {
		if (getRoot() instanceof IAnsarParent)
			((IAnsarParent) getRoot()).reset();
	}
}
