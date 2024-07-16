package ansarcontrols;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.Priority;
import model.PaginationData;
import model.Persister;
import utilities.ObjectChecker;

public class AnsarSearchableTable<T> extends AnsarVBox
{
    private AnsarHBox header;
    private AnsarComboBox<String> filterField;
    private AnsarComboBox<String> operaters;
    private AnsarTextField valTextField;
    private AnsarButton searchBtn;
    private AnsarButton nextBtn;
    private AnsarButton previousBtn;
    private AnsarTable<T> table;
    private Class<?> searchableClass;
    private PaginationData paginationData;

    public AnsarSearchableTable(Class<?> klass, AnsarTable<T> ansarTable)
    {
        AnsarVBox.setVgrow(this, Priority.ALWAYS);
        setSpacing(10);
        searchableClass = klass;
        header = new AnsarHBox();
        filterField = new AnsarComboBox<String>();
        filterField.config(Arrays.asList("code", "name"));
        operaters = new AnsarComboBox<>();
        operaters.config(Arrays.asList("equal", "like"));
        valTextField = new AnsarTextField();
        valTextField.setOnKeyPressed(e ->
        {
            if (ObjectChecker.areNotEqual(e.getCode(), KeyCode.ENTER))
                return;
            paginationData.setFirstResult(0);
            searchAction();
        });
        valTextField.textProperty().addListener(e ->
        {
            if (ObjectChecker.isNotEmptyOrZeroOrNull(valTextField.getText()))
                return;
            paginationData.setFirstResult(0);
            setTableItems("", new HashMap<>());
        });
        searchBtn = new AnsarButton("search");
        searchBtn.setOnAction(e ->
        {
            paginationData.setFirstResult(0);
            searchAction();
        });
        nextBtn = new AnsarButton("next");
        nextBtn.setOnAction(e ->
        {
            paginationData.next();
            searchAction();
        });
        previousBtn = new AnsarButton("previous");
        previousBtn.setOnAction(e ->
        {
            paginationData.previous();
            searchAction();
        });
        header.getChildren().addAll(filterField, operaters, valTextField, searchBtn, previousBtn, nextBtn);
        table = ansarTable;
        AnsarVBox.setVgrow(table, Priority.ALWAYS);
        setUpTable();
        getChildren().addAll(header, table);
    }

    public void setTableItems(String condition, Map<String, Object> params)
    {
        table.getItems().clear();
        table.getItems().addAll((List<T>) Persister.list(searchableClass, condition, params, paginationData));
    }

    private void searchAction()
    {
        String filterFieldName = filterField.fetchValue();
        if (ObjectChecker.isEmptyOrZeroOrNull(valTextField.fetchValue()))
        {
            setTableItems("", new HashMap<>());
            return;
        }
        String condition = " WHERE " + fetchConditionBasedOnOperator(filterFieldName, operaters.fetchValue());
        Map<String, Object> params = Persister.params(filterFieldName,
                fetchValBasedOnOperator(operaters.fetchValue(), valTextField.fetchValue()));
        setTableItems(condition, params);
    }

    public String fetchConditionBasedOnOperator(String property, String operatorName)
    {
        switch (operatorName)
        {
            case "like":
                return " " + property + " LIKE " + ":" + property + " ";
            default:
                return " " + property + " = " + ":" + property + " ";
        }
    }

    public String fetchValBasedOnOperator(String operator, String val)
    {
        if (ObjectChecker.areEqual(operator, "like"))
            return "%" + val + "%";
        return val;
    }

    private void setUpTable()
    {
        paginationData = new PaginationData(0, 15, Persister.countOf(searchableClass));
        setTableItems("", new HashMap<>());
    }

    @Override
    public void reset()
    {
        setUpTable();
        super.reset();
    }

}
