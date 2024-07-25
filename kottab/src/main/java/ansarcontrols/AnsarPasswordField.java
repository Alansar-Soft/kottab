package ansarcontrols;

import javafx.scene.control.PasswordField;
import javafx.scene.text.*;

public class AnsarPasswordField extends PasswordField implements IAnsarControl<String>
{

    public AnsarPasswordField()
    {
        this("");
    }

    public AnsarPasswordField(String promptText)
    {
        getStyleClass().add("textfield");
        setPrefWidth(200);
        setMinHeight(30);
        setFont(Font.font("Times New Roman", FontWeight.BLACK, 14));
        setPromptText(promptText);
    }

    @Override
    public void reset()
    {
        clear();
    }

    @Override
    public String fetchValue()
    {
        return getText();
    }

    @Override
    public void insertValue(String val)
    {
        setText(val);
    }

}
