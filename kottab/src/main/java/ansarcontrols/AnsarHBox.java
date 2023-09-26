package ansarcontrols;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

public class AnsarHBox extends HBox {
	public AnsarHBox() {
		this(new ArrayList());
	}

	public AnsarHBox(Node... nodes) {
		this(Arrays.asList(nodes));
	}

	public AnsarHBox(List<? extends Node> nodes) {
		setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
		setPadding(new Insets(5));
		setSpacing(5);
		getChildren().addAll(nodes);
	}

}
