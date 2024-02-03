package application;

import java.time.LocalDate;
import java.util.Arrays;

import ansarcontrols.AnsarComboBox;
import ansarcontrols.AnsarGridPane;
import ansarcontrols.AnsarLabeledControlHBox;
import ansarcontrols.AnsarScene;
import ansarcontrols.AnsarTable;
import ansarcontrols.AnsarTableColumn;
import ansarcontrols.AnsarTableRow;
import ansarcontrols.ControlType;
import entities.Address;
import entities.Person;
import javafx.scene.layout.Pane;
import utilities.DateTimeUtility;
import utilities.ObjectChecker;

public abstract class AbsPersonScreen<T extends Person> implements IFileScreen<T> {
	private AnsarScene scene;
	private AnsarLabeledControlHBox<String> codeBox;
	private AnsarLabeledControlHBox<String> registrationDateBox;
	private AnsarLabeledControlHBox<String> name;
	private AnsarLabeledControlHBox<LocalDate> birthdateDP;
	private AnsarLabeledControlHBox<Boolean> studyInAzharBox;
	private AnsarLabeledControlHBox<String> firstPhoneNo;
	private AnsarLabeledControlHBox<String> secondPhoneNo;
	private AnsarLabeledControlHBox<Country> country;
	private AnsarLabeledControlHBox<City> city;
	private AnsarLabeledControlHBox<Town> town;
	private AnsarGridPane headerContentPane;
	private AnsarTable<Person> table;
	private AnsarTableColumn<Person, Long> codeCol;
	private AnsarTableColumn<Person, String> registrationDateCol;
	private AnsarTableColumn<Person, String> nameCol;
	private AnsarTableColumn<Person, LocalDate> birthdateCol;
	private AnsarTableColumn<Person, String> azharStudentCol;
	private AnsarTableColumn<Person, String> firstPhoneNoCol;
	private AnsarTableColumn<Person, String> secondPhoneNoCol;
	private AnsarTableColumn<Person, String> countryCol;
	private AnsarTableColumn<Person, String> cityCol;
	private AnsarTableColumn<Person, String> townCol;

	@Override
	public Pane createHeaderContent() {
		headerContentPane = new AnsarGridPane();
		codeBox = new AnsarLabeledControlHBox<>("code", ControlType.Label);
		codeBox.insertValue(fetchCode());
		registrationDateBox = new AnsarLabeledControlHBox<>("registrationDate", ControlType.Label);
		registrationDateBox.insertValue(DateTimeUtility.fetchFormatedCurrentDateTime());
		name = new AnsarLabeledControlHBox<>("name", ControlType.TextField);
		birthdateDP = new AnsarLabeledControlHBox<>("birthdate", ControlType.DatePicker);
		country = new AnsarLabeledControlHBox<>("country", ControlType.ComboBox);
		city = new AnsarLabeledControlHBox<>("city", ControlType.ComboBox);
		town = new AnsarLabeledControlHBox<>("town", ControlType.ComboBox);
		AnsarComboBox<Country> countryCombo = country.getControl();
		countryCombo.config(Arrays.asList(Country.values()));
		AnsarComboBox<City> cityCombo = city.getControl();
		cityCombo.config(Arrays.asList(City.values()));
		AnsarComboBox<Town> townCombo = town.getControl();
		townCombo.config(Arrays.asList(Town.values()));
		firstPhoneNo = new AnsarLabeledControlHBox<>("firstPhoneNo", ControlType.TextField);
		secondPhoneNo = new AnsarLabeledControlHBox<>("secondPhoneNo", ControlType.TextField);
		studyInAzharBox = new AnsarLabeledControlHBox<>("azharStudent", ControlType.CheckBox);
		headerContentPane.add(codeBox, 0, 0);
		headerContentPane.add(registrationDateBox, 1, 0);
		headerContentPane.add(name, 0, 1);
		headerContentPane.add(birthdateDP, 1, 1);
		headerContentPane.add(studyInAzharBox, 2, 1);
		headerContentPane.add(country, 0, 2);
		headerContentPane.add(city, 1, 2);
		headerContentPane.add(town, 2, 2);
		headerContentPane.add(firstPhoneNo, 0, 3);
		headerContentPane.add(secondPhoneNo, 1, 3);
		return headerContentPane;
	}

	@Override
	public AnsarTable<T> createTableView() {
		table = new AnsarTable<>();
		table.setRowFactory(t -> {
			AnsarTableRow<Person> row = new AnsarTableRow<>();
			row.setOnMouseClicked(e -> {
				selectRowAction((T) row.getItem());
			});
			return row;
		});
		codeCol = new AnsarTableColumn<>("code");
		codeCol.config("code");
		registrationDateCol = new AnsarTableColumn<>("registrationDate");
		registrationDateCol.config("creationDate");
		nameCol = new AnsarTableColumn<>("name");
		nameCol.config("name");
		birthdateCol = new AnsarTableColumn<>("birthdate");
		birthdateCol.config("birthdate");
		firstPhoneNoCol = new AnsarTableColumn<>("firstPhoneNo");
		firstPhoneNoCol.config("firstPhoneNo");
		secondPhoneNoCol = new AnsarTableColumn<>("secondPhoneNo");
		secondPhoneNoCol.config("secondPhoneNo");
		countryCol = new AnsarTableColumn<>("country");
		countryCol.config("country");
		cityCol = new AnsarTableColumn<>("city");
		cityCol.config("city");
		townCol = new AnsarTableColumn<>("town");
		townCol.config("town");
		azharStudentCol = new AnsarTableColumn<>("azharStudent");
		azharStudentCol.config("azharStudentVal");
		table.getColumns().addAll(codeCol, registrationDateCol, nameCol, birthdateCol, firstPhoneNoCol,
				secondPhoneNoCol, countryCol, cityCol, townCol, azharStudentCol);
		return (AnsarTable<T>) table;
	}

	public void selectRowDefaultAction(Person item) {
		codeBox.insertValue(item.getCode());
		registrationDateBox.insertValue(DateTimeUtility.formatDateTime(item.getCreationDate()));
		name.insertValue(item.getName());
		birthdateDP.insertValue(item.getBirthdate());
		country.insertValue(item.getCountry());
		System.out.println(item.getCountry());
		city.insertValue(item.getCity());
		town.insertValue(item.getTown());
		firstPhoneNo.insertValue(item.getFirstPhoneNo());
		secondPhoneNo.insertValue(item.getSecondPhoneNo());
		studyInAzharBox.insertValue(item.getAzharStudent());
	}

	@Override
	public AnsarTable<T> fetchTable() {
		return (AnsarTable<T>) table;
	}

	@Override
	public T createEntity() {
		Person person = createPersonEntity();
		String code = this.codeBox.fetchValue();
		Long id = table.getItems().stream().filter(i -> ObjectChecker.areEqual(i.getCode(), code)).map(i -> i.getId())
				.findFirst().orElse(null);
		person.setId(id);
		person.setCode(code);
		person.setName(name.fetchValue());
		person.setBirthdate((LocalDate) birthdateDP.fetchValue());
		person.setCreationDate(DateTimeUtility.parseDateTime(registrationDateBox.fetchValue()));
		person.setFirstPhoneNo(ObjectChecker.toStringOrEmpty(firstPhoneNo.fetchValue()));
		person.setSecondPhoneNo(ObjectChecker.toStringOrEmpty(secondPhoneNo.fetchValue()));
		Address address = new Address();
		address.setCountry(country.fetchValue().name());
		address.setCity(city.fetchValue().name());
		address.setTown(town.fetchValue().name());
		person.setAddress(address);
		person.setAzharStudent((Boolean) studyInAzharBox.fetchValue());
		return (T) person;
	}

	public abstract Person createPersonEntity();

	@Override
	public AnsarScene fetchScene() {
		return scene;
	}

	public void setScene(AnsarScene scene) {
		this.scene = scene;
	}

	@Override
	public AnsarLabeledControlHBox<String> fetchCodeBox() {
		return codeBox;
	}

	@Override
	public AnsarLabeledControlHBox<String> fetchCreationDateBox() {
		return registrationDateBox;
	}

}
