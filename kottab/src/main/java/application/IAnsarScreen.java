package application;

import ansarcontrols.AnsarHBox;
import ansarcontrols.AnsarLabel;
import ansarcontrols.AnsarScene;
import ansarcontrols.AnsarVBox;
import ansarcontrols.AnsarVBoxRoot;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.control.Separator;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
        AnsarScene scene = new AnsarScene(root);
        return scene;
    }

    default Pane createHeaderBox()
    {
        AnsarLabel title = new AnsarLabel(Translator.translate(fetchScreenTitle()));
        title.setFont(Font.font("Times New Roman", FontWeight.EXTRA_BOLD, 40));
        return new AnsarVBox(title);
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
        return fetchScene();
    }

    default void updateRefFieldsData()
    {
        return;
    }
}
