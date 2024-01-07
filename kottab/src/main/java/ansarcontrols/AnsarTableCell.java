package ansarcontrols;

import javafx.scene.control.TableCell;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class AnsarTableCell<S, T> extends TableCell<S, T> {

	public AnsarTableCell() {
		super();
		setFont(Font.font("Times New Roman", FontWeight.BLACK, 14));	
	}

	protected void updateItem(T item, boolean empty) {
		super.updateItem(item, empty);
	}

}