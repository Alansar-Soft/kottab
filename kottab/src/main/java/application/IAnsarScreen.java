package application;

import ansarcontrols.*;
import javafx.geometry.*;
import javafx.scene.control.Separator;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import model.Persister;
import utilities.*;

public interface IAnsarScreen<T>
{

    default AnsarScene constructScreen()
    {
        AnsarVBoxRoot root = new AnsarVBoxRoot();
        Pane contentBox = createContentBox();
        AnsarVBox.setVgrow(contentBox, Priority.ALWAYS);
        Separator separator = new Separator(Orientation.HORIZONTAL);
        separator.setPrefHeight(15);
        separator.setValignment(VPos.TOP);
        root.getChildren().addAll(createHeaderBox(), separator, contentBox, createFooterBox());
        AnsarHBox menu = new AnsarHBox();
        menu.setStyle("-fx-border-style:SOLID;-fx-border-size:5");
        AnsarScene scene = new AnsarScene(new AnsarHBox(root, menu));
        updateRefFieldsData();
        return scene;
    }

    default Pane createHeaderBox()
    {
        AnsarLabel title = new AnsarLabel(Translator.translate(fetchScreenTitle()));
        title.setFont(Font.font("Times New Roman", FontWeight.EXTRA_BOLD, 40));
        AnsarButton listViewBtn = new AnsarButton("listView");
        return new AnsarVBox(new AnsarHBox(title, listViewBtn));
    }

    String fetchScreenTitle();

    Pane createContentBox();

    default Pane createFooterBox()
    {
        return new AnsarHBox();
    }

    default T submit()
    {
        T doc = createEntity();
        Result result = Persister.saveOrUpdate(doc);
        if (result.isFailed())
        {
            ResourceUtility.showError(result);
            return null;
        }
        reset();
        return doc;
    }

    T createEntity();

    default void reset()
    {
        fetchScene().reset();
    }

    AnsarScene fetchScene();

    default AnsarScene refreshScreen()
    {
        reset();
        updateRefFieldsData();
        return fetchScene();
    }

    default void updateRefFieldsData()
    {
        return;
    }
}
