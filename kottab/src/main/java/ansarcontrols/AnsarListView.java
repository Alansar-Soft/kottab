package ansarcontrols;

import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import utilities.*;

public class AnsarListView extends Dialog<ButtonType>
{
    public <T> AnsarListView(Class<?> klass, AnsarTable<T> table)
    {
        Callback<TableView<T>, TableRow<T>> oldRowFactory = table.getRowFactory();
        table.setRowFactory(tableView ->
        {
            TableRow<T> row = oldRowFactory.call(tableView);
            EventHandler<? super MouseEvent> onMouseClicked = row.getOnMouseClicked();
            row.setOnMouseClicked(e ->
            {
                onMouseClicked.handle(e);
                close();
            });
            return row;
        });
        AnsarSearchableTable<T> searchableTable = new AnsarSearchableTable<>(klass, table);
        getDialogPane().setContent(searchableTable);
        getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);
        ((Button) getDialogPane().lookupButton(ButtonType.APPLY)).setText(Translator.translate("Ok"));
        ((Button) getDialogPane().lookupButton(ButtonType.CANCEL)).setText(Translator.translate("Cancel"));
        initOwner(ResourceUtility.fetchStage());
        setResizable(true);
    }

}
