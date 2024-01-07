package ansarcontrols;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public interface IAnsarControl<T extends Object> extends IAnsarNode {
	 T fetchValue();

	 void insertValue(T val);
}
