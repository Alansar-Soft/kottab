package application;

import java.math.BigDecimal;
import java.util.ArrayList;

import ansarcontrols.AnsarButton;
import ansarcontrols.AnsarHBox;
import ansarcontrols.AnsarLabel;
import ansarcontrols.AnsarLabeledControlHBox;
import ansarcontrols.AnsarPane;
import ansarcontrols.AnsarScene;
import ansarcontrols.AnsarTable;
import ansarcontrols.AnsarVBox;
import ansarcontrols.AnsarVBoxRoot;
import entities.AnsarBaseEntity;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.Persister;
import utilities.DateTimeUtility;
import utilities.ObjectChecker;
import utilities.ResourceUtility;
import utilities.Result;

public interface IFileScreen<T> extends IAnsarScreen<T> {
	@Override
	default Pane createHeaderBox() {
		AnsarLabel title = new AnsarLabel(fetchScreenTitle());
		return new AnsarVBox(title, createHeaderContent(), createHeaderBtns());
	}

	Pane createHeaderContent();

	default Pane createHeaderBtns() {
		AnsarHBox btnsBox = new AnsarHBox();
		AnsarButton saveBtn = new AnsarButton(ResourceUtility.translate("save"));
		AnsarButton newBtn = new AnsarButton(ResourceUtility.translate("new"));
		AnsarButton backBtn = new AnsarButton(ResourceUtility.translate("back"));
		saveBtn.setOnAction(e -> submit());
		newBtn.setOnAction(e -> reset());
		backBtn.setOnAction(e -> ResourceUtility.fetchStage().setScene(HomeScreen.fetchScreen()));
		btnsBox.getChildren().addAll(saveBtn, newBtn, backBtn);
		return btnsBox;
	}

	@Override
	default T submit() {
		T doc = IAnsarScreen.super.submit();
		if (doc == null)
			return null;
		AnsarTable<T> table = fetchTable();
		ObservableList<T> items = table.getItems();
		boolean removed = items.removeIf(i -> ObjectChecker.areEqual(doc, i));
		items.add(doc);
		if (removed)
			items.sort((i1, i2) -> {
				if (i1 instanceof AnsarBaseEntity && i2 instanceof AnsarBaseEntity)
					return ((AnsarBaseEntity) i1).getCode().compareTo(((AnsarBaseEntity) i2).getCode());
				return 0;
			});
		table.refresh();
		return doc;
	}

	@Override
	default Pane createContentBox() {
		AnsarTable<T> table = createTableView();
		AnsarVBox.setVgrow(table, Priority.ALWAYS);
		return new AnsarVBox(table);
	}

	AnsarTable<T> createTableView();

	void selectRowAction(T item);

	Class<?> fetchDocumentClass();

	AnsarTable<T> fetchTable();

	@Override
	default T createEntity() {
		return null;
	}

	@Override
	default void reset() {
		IAnsarScreen.super.reset();
		fetchCodeBox().insertValue(fetchCode());
		fetchRegistrationDateBox().insertValue(DateTimeUtility.fetchFormatedCurrentDateTime());
	}

	AnsarLabeledControlHBox<String> fetchCodeBox();

	AnsarLabeledControlHBox<String> fetchRegistrationDateBox();

	default Long fetchCode() {
		BigDecimal lastCode = Persister.getSingleResultFromNativeQuery(
				"SELECT code FROM " + fetchDocumentClass().getSimpleName() + " ORDER BY code DESC ",
				Persister.params());
		return ObjectChecker.isEmptyOrZeroOrNull(lastCode) ? 1L : lastCode.longValue() + 1;
	}
}
