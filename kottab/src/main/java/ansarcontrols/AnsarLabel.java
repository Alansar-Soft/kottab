package ansarcontrols;

import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import utilities.*;

public class AnsarLabel extends Label implements IAnsarControl
{

    public AnsarLabel(String id)
    {
        super(Translator.translate(id));
        setPrefWidth(300);
        setFont(Font.font("Times New Roman", FontWeight.BOLD, 17));
        setWrapText(true);
    }

    public AnsarLabel()
    {
        this("");
    }

    @Override
    public String fetchValue()
    {
        return getText();
    }

    @Override
    public void insertValue(Object val)
    {
        setText(val.toString());
    }

    @Override
    public void reset()
    {
    }

}
