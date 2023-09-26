package ansarcontrols;

import javafx.scene.control.TableView;

public class AnsarTable<T> extends TableView<T> {
	public AnsarTable() {
		setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
//		setEditable(false);
	}
}
