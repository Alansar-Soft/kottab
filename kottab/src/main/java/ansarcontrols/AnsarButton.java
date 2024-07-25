package ansarcontrols;

import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import utilities.*;

public class AnsarButton extends Button
{

    public AnsarButton(String text)
    {
        super(Translator.translate(text));
        getStyleClass().add("btn");
        setFont(Font.font("Times New Roman", FontWeight.BLACK, 15));
    }

}
