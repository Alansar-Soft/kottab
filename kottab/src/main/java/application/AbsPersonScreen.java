package application;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import ansarcontrols.AnsarButton;
import ansarcontrols.AnsarComboBox;
import ansarcontrols.AnsarGridPane;
import ansarcontrols.AnsarHBox;
import ansarcontrols.AnsarLabeledControlHBox;
import ansarcontrols.AnsarScene;
import ansarcontrols.AnsarTable;
import ansarcontrols.AnsarTableColumn;
import ansarcontrols.AnsarTableRow;
import ansarcontrols.ControlType;
import entities.Address;
import entities.Person;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import model.Persister;
import utilities.ObjectChecker;
import utilities.ResourceUtility;

public abstract class AbsPersonScreen<T extends Person> implements IFileScreen<T> {
	private AnsarScene scene;
	private AnsarLabeledControlHBox<String> code;
	private AnsarLabeledControlHBox<LocalDateTime> registrationDateLabel;
	private AnsarLabeledControlHBox<String> name;
	private AnsarLabeledControlHBox<LocalDate> birthdateDP;
	private AnsarLabeledControlHBox<Boolean> studyInAzharBox;
	private AnsarLabeledControlHBox<String> firstPhoneNo;
	private AnsarLabeledControlHBox<String> secondPhoneNo;
	private AnsarLabeledControlHBox<Country> country;
	private AnsarLabeledControlHBox<City> city;
	private AnsarLabeledControlHBox<Town> town;
	private AnsarGridPane headerPane;
	private AnsarHBox btnsBox;
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
	public Pane createHeaderBox() {
		headerPane = new AnsarGridPane();
		code = new AnsarLabeledControlHBox("code", ControlType.Label);
		code.insertValue(fetchCode());
		registrationDateLabel = new AnsarLabeledControlHBox("registrationDate", ControlType.Label);
		registrationDateLabel.insertValue(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE).toString());
		name = new AnsarLabeledControlHBox("name", ControlType.TextField);
		birthdateDP = new AnsarLabeledControlHBox("birthdate", ControlType.DatePicker);
		country = new AnsarLabeledControlHBox("country", ControlType.ComboBox);
		city = new AnsarLabeledControlHBox("city", ControlType.ComboBox);
		town = new AnsarLabeledControlHBox("town", ControlType.ComboBox);
		((AnsarComboBox) country.getControl()).insertItems(ObjectChecker.translateList(Country.values()));
		((AnsarComboBox) city.getControl()).insertItems(ObjectChecker.translateList(City.values()));
		((AnsarComboBox) town.getControl()).insertItems(ObjectChecker.translateList(Town.values()));
		firstPhoneNo = new AnsarLabeledControlHBox("firstPhoneNo", ControlType.TextField);
		secondPhoneNo = new AnsarLabeledControlHBox("secondPhoneNo", ControlType.TextField);
		studyInAzharBox = new AnsarLabeledControlHBox("azharStudent", ControlType.CheckBox);
		headerPane.add(code, 0, 0);
		headerPane.add(registrationDateLabel, 1, 0);
		headerPane.add(name, 0, 1);
		headerPane.add(birthdateDP, 1, 1);
		headerPane.add(studyInAzharBox, 2, 1);
		headerPane.add(country, 0, 2);
		headerPane.add(city, 1, 2);
		headerPane.add(town, 2, 2);
		headerPane.add(firstPhoneNo, 0, 3);
		headerPane.add(secondPhoneNo, 1, 3);
		return headerPane;
	}

	@Override
	public AnsarTable<T> createTableView() {
		table = new AnsarTable<>();
		table.setPrefHeight(ResourceUtility.fetchScreenHeight() - (headerPane.getHeight() + btnsBox.getHeight()));
		table.setRowFactory(t -> {
			AnsarTableRow<Person> row = new AnsarTableRow<>();
			row.setOnMouseClicked(e -> {
				AnsarTableRow<Person> rowRef = row;
				selectRowAction((T) rowRef.getItem());
			});
			return row;
		});
		codeCol = new AnsarTableColumn<>("code");
		codeCol.setCellValueFactory(new PropertyValueFactory<Person, Long>("code"));
		registrationDateCol = new AnsarTableColumn<>("registrationDate");
		registrationDateCol.setCellValueFactory(new PropertyValueFactory<Person, String>("registrationDate"));
		nameCol = new AnsarTableColumn<>("name");
		nameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));
		birthdateCol = new AnsarTableColumn<>("birthdate");
		birthdateCol.setCellValueFactory(new PropertyValueFactory<Person, LocalDate>("birthdate"));
		firstPhoneNoCol = new AnsarTableColumn<>("firstPhoneNo");
		firstPhoneNoCol.setCellValueFactory(new PropertyValueFactory<Person, String>("firstPhoneNo"));
		secondPhoneNoCol = new AnsarTableColumn<>("secondPhoneNo");
		secondPhoneNoCol.setCellValueFactory(new PropertyValueFactory<>("secondPhoneNo"));
		countryCol = new AnsarTableColumn<>("country");
		countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
		cityCol = new AnsarTableColumn<>("city");
		cityCol.setCellValueFactory(new PropertyValueFactory<>("city"));
		townCol = new AnsarTableColumn<>("town");
		townCol.setCellValueFactory(new PropertyValueFactory<>("town"));
		azharStudentCol = new AnsarTableColumn<>("azharStudent");
		azharStudentCol.setCellValueFactory(new PropertyValueFactory<>("azharStudentVal"));
		table.getColumns().addAll(codeCol, registrationDateCol, nameCol, birthdateCol, firstPhoneNoCol,
				secondPhoneNoCol, countryCol, cityCol, townCol, azharStudentCol);
		table.getItems().clear();
		table.getItems().addAll((List<? extends Person>) Persister.list(fetchDocumentClass()));
		return (AnsarTable<T>) table;
	}

	public void selectRowDefaultAction(Person item) {
		code.insertValue(item.getCode());
		registrationDateLabel.insertValue(item.getRegistrationDate());
		name.insertValue(item.getName());
		birthdateDP.insertValue(item.getBirthdate());
		country.insertValue(item.getCountry());
		city.insertValue(item.getCity());
		town.insertValue(item.getTown());
		firstPhoneNo.insertValue(item.getFirstPhoneNo());
		secondPhoneNo.insertValue(item.getSecondPhoneNo());
		studyInAzharBox.insertValue(item.getAzharStudent());
	}

	@Override
	public Pane createBtnsBox() {
		btnsBox = new AnsarHBox();
		AnsarButton saveBtn = new AnsarButton(ResourceUtility.id("Save"));
		AnsarButton backBtn = new AnsarButton(ResourceUtility.id("Back"));
		saveBtn.setOnAction(e -> submit());
		backBtn.setOnAction(e -> ResourceUtility.fetchStage().setScene(HomeScreen.fetchScreen()));
		btnsBox.getChildren().addAll(saveBtn, backBtn);
		return btnsBox;
	}

	@Override
	public T fetchFile() {
		Long code = Long.valueOf(this.code.fetchValue());
		Person person = table.getItems().stream().filter(p -> ObjectChecker.areEqual(p.getCode(), code)).findFirst()
				.orElse(null);
		if (ObjectChecker.isEmptyOrNull(person)) {
			person = createEntity();
			table.getItems().add(person);
		}
		person.setCode(code);
		person.setName(name.fetchValue());
		person.setBirthdate((LocalDate) birthdateDP.fetchValue());
		person.setRegistrationDate(LocalDateTime.now());
		person.setFirstPhoneNo(ObjectChecker.toString(firstPhoneNo.fetchValue()));
		person.setSecondPhoneNo(ObjectChecker.toString(secondPhoneNo.fetchValue()));
		Address address = new Address();
		address.setCountry(ObjectChecker.toString(country.fetchValue()));
		address.setCity(ObjectChecker.toString(city.fetchValue()));
		address.setTown(ObjectChecker.toString(town.fetchValue()));
		person.setAddress(address);
		person.setAzharStudent((Boolean) studyInAzharBox.fetchValue());
		return (T) person;
	}

	public abstract Person createEntity();

	@Override
	public void reset() {
		code.insertValue(fetchCode());
		registrationDateLabel.insertValue(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE).toString());
		name.reset();
		birthdateDP.reset();
		country.reset();
		city.reset();
		town.reset();
		firstPhoneNo.reset();
		secondPhoneNo.reset();
		studyInAzharBox.reset();
		table.refresh();
	}

	@Override
	public AnsarScene fetchScene() {
		return scene;
	}

	public void setScene(AnsarScene scene) {
		this.scene = scene;
	}

	@Override
	public AnsarScene refreshScreen() {
		registrationDateLabel.insertValue(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE).toString());
		return scene;
	}
}
