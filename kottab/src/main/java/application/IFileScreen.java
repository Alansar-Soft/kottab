package application;

import ansarcontrols.AnsarScene;
import ansarcontrols.AnsarTable;
import ansarcontrols.AnsarVBoxRoot;
import javafx.scene.layout.Pane;
import model.Persister;
import utilities.ObjectChecker;
import utilities.ResourceUtility;

public interface IFileScreen<T> {
	default AnsarScene constructScreen() {
		AnsarVBoxRoot root = new AnsarVBoxRoot();
		root.getChildren().addAll(createHeaderBox(), createBtnsBox(), createTableView());
		AnsarScene scene = new AnsarScene(root, ResourceUtility.fetchScreenWidth(),
				ResourceUtility.fetchScreenHeight() - 100);
		return scene;
	}

	Pane createHeaderBox();

	Pane createBtnsBox();

	default void submit() {
		T doc = fetchFile();
		Persister.save(doc);
		reset();
	}

	T fetchFile();

	void reset();

	AnsarTable<T> createTableView();

	void selectRowAction(T item);

	AnsarScene fetchScene();

	Class<?> fetchDocumentClass();

	default Long fetchCode() {
		Long lastCode = Persister.fetchFirstRow(
				"SELECT code FROM " + fetchDocumentClass().getName() + " ORDER BY code DESC ", Persister.params());
		return ObjectChecker.isEmptyOrNull(lastCode) ? 1L : lastCode + 1;
	}

	default AnsarScene refreshScreen() {
		return fetchScene();
	}

}
