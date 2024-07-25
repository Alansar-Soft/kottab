package application;

import ansarcontrols.*;
import javafx.scene.control.*;
import javafx.scene.layout.TilePane;
import utilities.*;
import utilities.reports.ReportManager;

import java.time.LocalDate;
import java.util.*;

public class QuestionsDialog extends Dialog<ButtonType>
{
    private Map<String, AnsarLabeledControlHBox<?>> questions = new HashMap<>();
    private TilePane container = new TilePane();

    // dialog that take three parameters from-to date and student code
    // create report dialog controller
    // use builder pattern with report dialog
    // TODO: ٢٤/٠٧/٢٠٢٤ make it questions dialog - make it more general
    public QuestionsDialog()
    {
        initOwner(ResourceUtility.fetchStage());
        getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);
        Button okBtn = (Button) getDialogPane().lookupButton(ButtonType.APPLY);
        okBtn.setText(Translator.translate("Ok"));
        ((Button) getDialogPane().lookupButton(ButtonType.CANCEL)).setText(Translator.translate("Cancel"));
        okBtn.setOnAction(actionEvent ->
                ReportManager.runReport("kottab/src/main/resources/Invoice.jrxml", fetchParams()));
        container.setPrefColumns(3);
        getDialogPane().setContent(container);
    }

    private Map<String, Object> fetchParams()
    {
        HashMap<String, Object> params = new HashMap<>();
        for (Map.Entry<String, AnsarLabeledControlHBox<?>> entry : questions.entrySet())
        {
            params.put(entry.getKey(), fetchValue(entry.getValue().fetchValue()));
        }
        return params;
    }


    private <T extends Object> T fetchValue(Object object)
    {
        if (object instanceof Integer)
            return (T) Integer.valueOf(ObjectChecker.toStringOrEmpty(object));
        if (object instanceof LocalDate)
            return (T) DateTimeUtility.dateFromLocalDate((LocalDate) object);
        return null;
    }

    public void addQuestion(String field, ControlType controlType)
    {
        AnsarLabeledControlHBox<Object> control = new AnsarLabeledControlHBox<>(field, controlType);
        questions.put(field, control);
        container.getChildren().add(control);
    }

    public void showDialog()
    {
        showAndWait();
        questions.clear();
        container.getChildren().clear();
    }

}
