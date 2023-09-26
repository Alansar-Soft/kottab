package ansarcontrols;

import java.util.function.Function;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import utilities.ObjectChecker;
import utilities.ResourceUtility;

public class AnsarTableColumn<S, T> extends TableColumn<S, T> {
	public AnsarTableColumn(String name) {
		setText(ResourceUtility.id(name));
	}

	public void config(Callback<CellDataFeatures<S, T>, ObservableValue<T>> cellValueFactory,
			Function<T, String> toStrFun) {
		setCellValueFactory(cellValueFactory);
		setCellFactory(c -> {
			AnsarTableCell<S, T> cell = new AnsarTableCell<S, T>() {

				@Override
				protected void updateItem(T item, boolean empty) {
					super.updateItem(item, empty);
					if (empty || ObjectChecker.isEmptyOrNull(item)) {
						setText("");
						return;
					}
					setText(toStrFun.apply(item));
				}

			};
			return cell;
		});
	}
}
