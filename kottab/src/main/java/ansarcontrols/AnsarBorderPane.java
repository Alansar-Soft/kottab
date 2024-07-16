package ansarcontrols;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;

import java.util.List;

public class AnsarBorderPane extends BorderPane implements IAnsarParent
{

    @Override
    public List<? extends Node> fetchChildren()
    {
        return getChildren();
    }
}
