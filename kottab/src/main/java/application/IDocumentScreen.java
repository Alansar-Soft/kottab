package application;

import ansarcontrols.*;
import entities.AnsarBaseEntity;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;
import model.Persister;
import utilities.*;

public interface IDocumentScreen<T> extends IAnsarScreen<T>
{
    @Override
    default AnsarScene constructScreen()
    {
        AnsarScene screen = IAnsarScreen.super.constructScreen();
        updateRefFieldsData();
        return screen;

    }

    @Override
    default Pane createContentBox()
    {
        AnsarVBox contentBox = new AnsarVBox(createContent());
        AnsarHBox btnsBox = new AnsarHBox();
        AnsarButton saveBtn = new AnsarButton("save");
        AnsarButton newBtn = new AnsarButton("new");
        AnsarButton listViewBtn = new AnsarButton("listView");
        saveBtn.setOnAction(e -> submit());
        newBtn.setOnAction(e -> reset());
        AnsarListView listView = new AnsarListView(fetchDocumentClass(), createListViewTable());
        listViewBtn.setOnAction(e -> listView.showAndWait());
        btnsBox.getChildren().addAll(saveBtn, newBtn, listViewBtn);
        contentBox.getChildren().add(btnsBox);
        return contentBox;
    }

    Pane createContent();

    AnsarTable<T> createListViewTable();

    void selectRowAction(T item);

    @Override
    default T submit()
    {
        T doc = IAnsarScreen.super.submit();
        if (doc == null) return null;
        AnsarTable<T> table = fetchTable();
        ObservableList<T> items = table.getItems();
        boolean removed = items.removeIf(i -> ObjectChecker.areEqual(doc, i));
        items.add(doc);
        if (removed) items.sort((i1, i2) ->
        {
            if (i1 instanceof AnsarBaseEntity && i2 instanceof AnsarBaseEntity)
                return ((AnsarBaseEntity) i1).getCode().compareTo(((AnsarBaseEntity) i2).getCode());
            return 0;
        });
        table.refresh();
        return doc;
    }

    default String fetchCode()
    {
        String lastCode = Persister.getSingleResultFromNativeQuery("SELECT code FROM " + fetchDocumentClass().getSimpleName() + " ORDER BY code DESC ", Persister.params());
        return ObjectChecker.toStringOrEmpty(ObjectChecker.isEmptyOrZeroOrNull(lastCode) ? 1L : Long.valueOf(lastCode) + 1);
    }

    Class<?> fetchDocumentClass();

    @Override
    default void reset()
    {
        IAnsarScreen.super.reset();
        fetchCodeBox().insertValue(fetchCode());
        fetchCreationDateBox().insertValue(DateTimeUtility.fetchFormatedCurrentDateTime());
    }

    AnsarLabeledControlHBox<String> fetchCodeBox();

    AnsarLabeledControlHBox<String> fetchCreationDateBox();

    AnsarTable<T> fetchTable();

    @Override
    default AnsarScene refreshScreen()
    {
        updateRefFieldsData();
        return IAnsarScreen.super.refreshScreen();
    }

}
