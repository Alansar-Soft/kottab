package ansarcontrols;

import java.util.List;

import javafx.scene.Node;

public interface IAnsarParent extends IAnsarNode {
	@Override
	default public void reset() {
		for (Node node : fetchChildren()) {
			if (node instanceof IAnsarNode)
				((IAnsarNode) node).reset();
		}
	}

	List<? extends Node> fetchChildren();
}
