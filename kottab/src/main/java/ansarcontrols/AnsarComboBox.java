package ansarcontrols;

import entities.files.AnsarFile;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.text.*;
import javafx.util.StringConverter;
import utilities.*;

import java.util.*;
import java.util.function.Function;

public class AnsarComboBox<T> extends ComboBox<T> implements IAnsarControl<T>
{
    public AnsarComboBox()
    {
        getStyleClass().add("textfield");
        setPrefWidth(200);
        getEditor().setFont(Font.font("Times New Roman", FontWeight.BLACK, 14));
    }

    public void insertItems(Collection<T> items)
    {
        getItems().clear();
        getItems().addAll(items);
        getSelectionModel().selectFirst();
    }

    public void insertItems(T... items)
    {
        insertItems(Arrays.asList(items));
    }

    @Override
    public T fetchValue()
    {
        return getSelectionModel().getSelectedItem();
    }

    @Override
    public void insertValue(T val)
    {
        T item = CollectionsUtility.fetchFirstMatched(getItems(), i -> ObjectChecker.areEqual(i, val));
        getSelectionModel().select(item);
    }

    @Override
    public void reset()
    {
        getSelectionModel().selectFirst();
    }

    public void config(List<T> items)
    {
        if (ObjectChecker.isEmptyOrZeroOrNull(items))
            return;
        if (items.get(0) instanceof AnsarFile)
            applyStringConverter(entity -> ((AnsarFile) entity).getName());
        else if (items.get(0) instanceof String)
            applyStringConverter(i -> Translator.translate((String) i));
        else if (items.get(0) instanceof Enum)
            applyStringConverter(i -> Translator.translate(((Enum<?>) i).name()));
        insertItems(FXCollections.observableArrayList(items));
    }

    public void config(List<T> items, Function<T, String> toStrFun)
    {
        applyStringConverter(toStrFun);
        insertItems(FXCollections.observableArrayList(items));
    }

    public void applyStringConverter(Function<T, String> toStrFun)
    {
        applyStringConverter(toStrFun, i -> getValue());
    }

    public void applyStringConverter(Function<T, String> toStrFun, Function<String, T> fromStrFun)
    {
        setConverter(new StringConverter<T>()
        {

            @Override
            public String toString(T item)
            {
                if (ObjectChecker.isEmptyOrZeroOrNull(item))
                    return "";
                return ObjectChecker.toStringOrEmpty(toStrFun.apply(item));
            }

            @Override
            public T fromString(String str)
            {
                return fromStrFun.apply(str);
            }
        });
    }

}
