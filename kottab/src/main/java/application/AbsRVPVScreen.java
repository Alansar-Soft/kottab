package application;

import ansarcontrols.*;
import entities.documents.*;
import javafx.scene.layout.Pane;
import utilities.*;

import java.math.BigDecimal;

public abstract class AbsRVPVScreen<T extends AbsRVPV> implements IDocumentScreen<T>
{
    private AnsarScene scene;
    private AnsarLabeledControlHBox<String> codeField;
    private AnsarLabeledControlHBox<String> creationDateField;
    private AnsarLabeledControlHBox<BigDecimal> amountField;
    private AnsarLabeledControlHBox<String> remarksField;
    private AnsarTable<T> table;
    private AnsarTableColumn<T, String> codeCol;
    private AnsarTableColumn<T, String> creationDateCol;
    private AnsarTableColumn<T, BigDecimal> amountCol;
    private AnsarTableColumn<T, String> remarksCol;

    @Override
    public Pane createContent()
    {
        codeField = new AnsarLabeledControlHBox<>("code", ControlType.Label);
        codeField.insertValue(fetchCode());
        creationDateField = new AnsarLabeledControlHBox<>("creationDate", ControlType.Label);
        creationDateField.insertValue(DateTimeUtility.fetchFormatedCurrentDateTime());
        amountField = new AnsarLabeledControlHBox<>("amount", ControlType.BigDecimalField);
        remarksField = new AnsarLabeledControlHBox<>("remarks", ControlType.TextArea);
        AnsarGridPane pane = new AnsarGridPane();
        pane.add(codeField, 0, 0);
        pane.add(creationDateField, 1, 0);
        pane.add(amountField, 0, 1);
        pane.add(remarksField, 0, 2, 2, 1);
        return pane;
    }

    @Override
    public AnsarTable createListViewTable()
    {
        table = new AnsarTable<>();
        table.setRowFactory(r ->
        {
            AnsarTableRow<T> row = new AnsarTableRow<>();
            row.setOnMouseClicked(e -> selectRowAction(row.getItem()));
            return row;
        });
        codeCol = new AnsarTableColumn<>("code");
        codeCol.config("code");
        creationDateCol = new AnsarTableColumn<>("creationDate");
        creationDateCol.config("creationDate");
        amountCol = new AnsarTableColumn<>("amount");
        amountCol.config("amount");
        remarksCol = new AnsarTableColumn<>("remarks");
        remarksCol.config("remarks");
        table.getColumns().addAll(codeCol, creationDateCol, amountCol, remarksCol);
        return table;
    }

    @Override
    public void selectRowAction(T rvpv)
    {
        codeField.insertValue(rvpv.getCode());
        creationDateField.insertValue(DateTimeUtility.formatDateTime(rvpv.getCreationDate()));
        amountField.insertValue(rvpv.getAmount());
        remarksField.insertValue(rvpv.getRemarks());
    }

    @Override
    public T createEntity()
    {
        PaymentVoucher pv = new PaymentVoucher();
        String code = codeField.fetchValue();
        Long id = table.getItems().stream().filter(i -> ObjectChecker.areEqual(i.getCode(), code)).map(i -> i.getId())
                .findFirst().orElse(null);
        pv.setId(id);
        pv.setCode(code);
        pv.setCreationDate(DateTimeUtility.parseDateTime(creationDateField.fetchValue()));
        pv.setAmount(amountField.fetchValue());
        pv.setRemarks(remarksField.fetchValue());
        return (T) pv;
    }

    @Override
    public AnsarScene fetchScene()
    {
        return scene;
    }

    @Override
    public Class<?> fetchDocumentClass()
    {
        return PaymentVoucher.class;
    }

    @Override
    public AnsarLabeledControlHBox<String> fetchCodeBox()
    {
        return codeField;
    }

    @Override
    public AnsarLabeledControlHBox<String> fetchCreationDateBox()
    {
        return creationDateField;
    }

    @Override
    public AnsarTable<T> fetchTable()
    {
        return table;
    }

    public void construct()
    {
        scene = constructScreen();
    }
}
