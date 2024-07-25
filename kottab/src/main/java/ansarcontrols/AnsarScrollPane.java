package ansarcontrols;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;

import java.util.*;

public class AnsarScrollPane extends ScrollPane implements IAnsarParent
{

    public AnsarScrollPane()
    {
    }

    public AnsarScrollPane(Node node)
    {
        super(node);
    }

    @Override
    public List<? extends Node> fetchChildren()
    {
        return Arrays.asList(getContent());
    }
}
