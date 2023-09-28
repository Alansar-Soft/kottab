package ansarcontrols;

import javafx.scene.control.TableView;

public class AnsarTable<T> extends TableView<T> implements IAnsarNode {
	public AnsarTable() {
		setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
	}

	@Override
	public void reset() {
		refresh();
	}
}
