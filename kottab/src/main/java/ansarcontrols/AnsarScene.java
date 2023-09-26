package ansarcontrols;

import javafx.geometry.NodeOrientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import utilities.ResourceUtility;

public class AnsarScene extends Scene {

	public AnsarScene(Parent root) {
		super(root, ResourceUtility.fetchScreenWidth(), ResourceUtility.fetchScreenHeight());
		setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
	}

	public AnsarScene(Parent root, double width, double height) {
		super(root, width, height);
		setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
	}

}
