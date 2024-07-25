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
        AnsarBorderPane root = new AnsarBorderPane();
        Pane top = createHeaderBox();
        Separator separator = new Separator(Orientation.HORIZONTAL);
//        separator.setPrefHeight(15);
        separator.setValignment(VPos.BOTTOM);
        AnsarVBox node = new AnsarVBox(top, separator);
        node.getStyleClass().add("nav-form");

        root.setTop(node);

        Pane contentBox = createContentBox();
        AnsarVBox.setVgrow(contentBox, Priority.ALWAYS);
        contentBox.setStyle("-fx-background-color:#fff;");
        root.setCenter(contentBox);

        root.setRight(new SideBar());

        root.setBottom(createFooterBox());

        AnsarScene scene = new AnsarScene(root);
        updateRefFieldsData();
        return scene;
    }

    default Pane createHeaderBox()
    {
        AnsarLabel title = new AnsarLabel(Translator.translate(fetchScreenTitle()));
        title.setFont(Font.font("Times New Roman", FontWeight.EXTRA_BOLD, 40));
        AnsarButton listViewBtn = new AnsarButton("listView");
        AnsarHBox hBox = new AnsarHBox(title);
        hBox.setAlignment(Pos.CENTER);
        return new AnsarVBox(hBox);
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
