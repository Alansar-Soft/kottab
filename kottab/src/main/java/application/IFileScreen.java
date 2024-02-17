package application;

import ansarcontrols.*;
import entities.AnsarBaseEntity;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;
import model.Persister;
import utilities.*;

public interface IFileScreen<T> extends IAnsarScreen<T>
{

    @Override
    default AnsarScene constructScreen()
    {
        AnsarScene screen = IAnsarScreen.super.constructScreen();
        updateRefFieldsData();
        return screen;

    }

    @Override
    default Pane createHeaderBox()
    {
        Pane headerBox = IAnsarScreen.super.createHeaderBox();
        headerBox.getChildren().addAll(createHeaderContent(), createHeaderBtns());
        return headerBox;
    }

    Pane createHeaderContent();

    default Pane createHeaderBtns()
    {
        AnsarHBox btnsBox = new AnsarHBox();
        AnsarButton saveBtn = new AnsarButton("save");
        AnsarButton newBtn = new AnsarButton("new");
        AnsarButton backBtn = new AnsarButton("back");
        saveBtn.setOnAction(e -> submit());
        newBtn.setOnAction(e -> reset());
        backBtn.setOnAction(e -> ResourceUtility.fetchStage().setScene(HomeScreen.fetchScreen()));
        btnsBox.getChildren().addAll(saveBtn, newBtn, backBtn);
        return btnsBox;
    }

    @Override
    default T submit()
    {
        T doc = IAnsarScreen.super.submit();
        if (doc == null)
            return null;
        AnsarTable<T> table = fetchTable();
        ObservableList<T> items = table.getItems();
        boolean removed = items.removeIf(i -> ObjectChecker.areEqual(doc, i));
        items.add(doc);
        if (removed)
            items.sort((i1, i2) ->
            {
                if (i1 instanceof AnsarBaseEntity && i2 instanceof AnsarBaseEntity)
                    return ((AnsarBaseEntity) i1).getCode().compareTo(((AnsarBaseEntity) i2).getCode());
                return 0;
            });
        table.refresh();
        return doc;
    }

    @Override
    default Pane createContentBox()
    {
        return new AnsarSearchableTable(fetchDocumentClass(), createTableView());
    }

    AnsarTable<T> createTableView();

    void selectRowAction(T item);

    Class<?> fetchDocumentClass();

    AnsarTable<T> fetchTable();

    @Override
    default void reset()
    {
        IAnsarScreen.super.reset();
        fetchCodeBox().insertValue(fetchCode());
        fetchCreationDateBox().insertValue(DateTimeUtility.fetchFormatedCurrentDateTime());
    }

    AnsarLabeledControlHBox<String> fetchCodeBox();

    AnsarLabeledControlHBox<String> fetchCreationDateBox();

    default String fetchCode()
    {
        String lastCode = Persister.getSingleResultFromNativeQuery(
                "SELECT code FROM " + fetchDocumentClass().getSimpleName() + " ORDER BY code DESC ",
                Persister.params());
        return ObjectChecker
                .toStringOrEmpty(ObjectChecker.isEmptyOrZeroOrNull(lastCode) ? 1L : Long.valueOf(lastCode) + 1);
    }

    @Override
    default AnsarScene refreshScreen()
    {
        updateRefFieldsData();
        return IAnsarScreen.super.refreshScreen();
    }

}
