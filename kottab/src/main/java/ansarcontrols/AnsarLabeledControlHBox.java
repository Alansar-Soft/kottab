package ansarcontrols;

import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import utilities.*;

public class AnsarLabeledControlHBox<T> extends HBox implements IAnsarControl<T>
{
    private Label titleLabel;
    @SuppressWarnings("rawtypes")
    private IAnsarControl control;

    public AnsarLabeledControlHBox(String title, ControlType controlType)
    {
        titleLabel = new AnsarLabel(Translator.translate(title));
        titleLabel.setPrefWidth(100);
        control = buildControl(controlType);
        setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
        setPadding(new Insets(5));
        setSpacing(5);
        getChildren().addAll(titleLabel, (Control) control);
    }

    private IAnsarControl buildControl(ControlType controlType)
    {
        if (ObjectChecker.areEqual(ControlType.TextField, controlType))
            return new AnsarTextField();
        if (ObjectChecker.areEqual(ControlType.PasswordField, controlType))
            return new AnsarPasswordField();
        if (ObjectChecker.areEqual(ControlType.DatePicker, controlType))
            return new AnsarDatePicker();
        if (ObjectChecker.areEqual(ControlType.ComboBox, controlType))
            return new AnsarComboBox<>();
        if (ObjectChecker.areEqual(ControlType.CheckBox, controlType))
            return new AnsarCheckBox();
        if (ObjectChecker.areEqual(ControlType.Label, controlType))
            return new AnsarLabel();
        if (ObjectChecker.areEqual(ControlType.TextArea, controlType))
            return new AnsarTextArea();
        return null;
    }

    public <T extends Control> T getControl()
    {
        return (T) control;
    }

    public void reset()
    {
        control.reset();
    }

    @SuppressWarnings({"unchecked"})
    public void insertValue(Object val)
    {
        control.insertValue(val);
    }

    @Override
    public T fetchValue()
    {
        return (T) control.fetchValue();
    }
}
