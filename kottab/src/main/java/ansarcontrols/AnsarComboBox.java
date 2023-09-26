package ansarcontrols;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;
import utilities.CollectionsUtility;
import utilities.ObjectChecker;

public class AnsarComboBox<T> extends ComboBox<T> implements IAnsarControl<T> {
	public AnsarComboBox() {
		setId("ansarComboBox");
		setPrefWidth(200);
	}

	public void insertItems(Collection<T> items) {
		getItems().clear();
		getItems().addAll(items);
		getSelectionModel().selectFirst();
	}

	public void insertItems(T... items) {
		insertItems(Arrays.asList(items));
	}

	@Override
	public T fetchValue() {
		return getSelectionModel().getSelectedItem();
	}

	@Override
	public void insertValue(T val) {
		T item = CollectionsUtility.fetchFirstMatched(getItems(), i -> ObjectChecker.areEqual(i, val));
		getSelectionModel().select(item);
	}

	@Override
	public void reset() {
		getSelectionModel().selectFirst();
	}

	public void config(ObservableList<T> items, Function<T, String> toStrFun) {
		setItems(items);
		applyStringConverter(toStrFun);
	}

	public void applyStringConverter(Function<T, String> toStrFun, Function<String, T> fromStrFun) {
		setConverter(new StringConverter<T>() {

			@Override
			public String toString(T item) {
				if (ObjectChecker.isEmptyOrNull(item))
					return "";
				return toStrFun.apply(item);
			}

			@Override
			public T fromString(String str) {
				return fromStrFun.apply(str);
			}
		});
	}

	public void applyStringConverter(Function<T, String> toStrFun) {
		setConverter(new StringConverter<T>() {

			@Override
			public String toString(T item) {
				if (ObjectChecker.isEmptyOrNull(item))
					return "";
				return ObjectChecker.toString(toStrFun.apply(item));
			}

			@Override
			public T fromString(String str) {
				return getValue();
			}
		});
	}

}
