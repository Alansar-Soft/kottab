package ansarcontrols;

import javafx.scene.control.TextField;
import javafx.scene.text.*;
import utilities.ObjectChecker;

public class AnsarTextField<T> extends TextField implements IAnsarControl<Object>
{
    public AnsarTextField()
    {
        this("");
    }

    public AnsarTextField(String promptText)
    {
        setPrefWidth(200);
        setMinHeight(30);
        setFont(Font.font("Times New Roman", FontWeight.BLACK, 14));
        getStyleClass().add("textfield");
        setPromptText(promptText);
    }

    @Override
    public T fetchValue()
    {
        return (T) getText();
    }

    @Override
    public void insertValue(Object val)
    {
        setText(ObjectChecker.toStringOrEmpty(val));
    }

    @Override
    public void reset()
    {
        clear();
    }

}
