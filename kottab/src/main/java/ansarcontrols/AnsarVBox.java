package ansarcontrols;

import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.util.*;

public class AnsarVBox extends VBox implements IAnsarParent
{

    public AnsarVBox()
    {
        this(new ArrayList<>());
    }

    public AnsarVBox(Node... nodes)
    {
        this(Arrays.asList(nodes));
    }

    public AnsarVBox(List<Node> nodes)
    {
        getStyleClass().add("v-box");
        setPadding(new Insets(5));
        setSpacing(5);
        setNodeOrientation(NodeOrientation.INHERIT);
        getChildren().addAll(nodes);
    }

    @Override
    public List<? extends Node> fetchChildren()
    {
        return getChildren();
    }
}
