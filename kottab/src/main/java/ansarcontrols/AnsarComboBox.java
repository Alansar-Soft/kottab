package ansarcontrols;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import entities.AnsarBaseEntity;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.StringConverter;
import utilities.*;

public class AnsarComboBox<T> extends ComboBox<T> implements IAnsarControl<T>
{
    public AnsarComboBox()
    {
        setId("ansarComboBox");
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
        if (items.get(0) instanceof AnsarBaseEntity)
            applyStringConverter(entity -> ((AnsarBaseEntity) entity).getName());
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
