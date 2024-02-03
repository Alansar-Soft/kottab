package application;

import ansarcontrols.AnsarComboBox;
import ansarcontrols.AnsarGridPane;
import ansarcontrols.AnsarLabeledControlHBox;
import ansarcontrols.AnsarScene;
import ansarcontrols.AnsarTable;
import ansarcontrols.AnsarTableColumn;
import ansarcontrols.AnsarTableRow;
import ansarcontrols.ControlType;
import entities.AnsarBaseEntity;
import entities.AnsarUser;
import entities.UserDetails;
import javafx.scene.layout.Pane;
import utilities.DateTimeUtility;
import utilities.Entities;
import utilities.ObjectChecker;

public class UserScreen implements IFileScreen<AnsarUser> {
	private AnsarScene scene;
	private AnsarLabeledControlHBox<String> code;
	private AnsarLabeledControlHBox<String> name;
	private AnsarLabeledControlHBox<String> creationDate;
	private AnsarLabeledControlHBox<String> personCode;
	private AnsarLabeledControlHBox<String> personType;
	private AnsarLabeledControlHBox<String> userName;
	private AnsarLabeledControlHBox<String> password;
	private AnsarTable<AnsarUser> table;
	private AnsarTableColumn<AnsarUser, String> codeCol;
	private AnsarTableColumn<AnsarUser, String> nameCol;
	private AnsarTableColumn<AnsarUser, String> creationDateCol;
	private AnsarTableColumn<AnsarUser, String> personCodeCol;
	private AnsarTableColumn<AnsarUser, String> personTypeCol;
	private AnsarTableColumn<AnsarUser, String> userNameCol;

	public UserScreen() {
		this.scene = constructScreen();
	}

	@Override
	public String fetchScreenTitle() {
		return "usersScreen";
	}

	@Override
	public AnsarScene fetchScene() {
		if (scene == null)
			scene = constructScreen();
		return scene;
	}

	@Override
	public Pane createHeaderContent() {
		code = new AnsarLabeledControlHBox<>("code", ControlType.Label);
		code.insertValue(fetchCode());
		name = new AnsarLabeledControlHBox<>("name", ControlType.TextField);
		creationDate = new AnsarLabeledControlHBox<>("creationDate", ControlType.Label);
		creationDate.insertValue(DateTimeUtility.fetchFormatedCurrentDateTime());
		personType = new AnsarLabeledControlHBox<>("personType", ControlType.ComboBox);
		AnsarComboBox<String> personTypeComboBox = personType.getControl();
		personTypeComboBox.config(Entities.PersonEntities);
		personCode = new AnsarLabeledControlHBox<>("personCode", ControlType.TextField);
		userName = new AnsarLabeledControlHBox<>("userName", ControlType.TextField);
		password = new AnsarLabeledControlHBox<>("password", ControlType.PasswordField);
		AnsarGridPane headerPane = new AnsarGridPane();
		headerPane.add(code, 0, 0);
		headerPane.add(creationDate, 1, 0);
		headerPane.add(name, 0, 1);
		headerPane.add(personType, 0, 2);
		headerPane.add(personCode, 1, 2);
		headerPane.add(userName, 0, 3);
		headerPane.add(password, 1, 3);
		return headerPane;
	}

	@Override
	public AnsarTable<AnsarUser> createTableView() {
		table = new AnsarTable<>();
		table.setRowFactory(r -> {
			AnsarTableRow<AnsarUser> row = new AnsarTableRow<>();
			row.setOnMouseClicked(e -> selectRowAction(row.getItem()));
			return row;
		});
		codeCol = new AnsarTableColumn<>("code");
		codeCol.config("code");
		nameCol = new AnsarTableColumn<>("name");
		nameCol.config("name");
		creationDateCol = new AnsarTableColumn<>("creationDate");
		creationDateCol.config("creationDate");
		personTypeCol = new AnsarTableColumn<>("personType");
		personTypeCol.config("personType");
		personCodeCol = new AnsarTableColumn<>("personCode");
		personCodeCol.config("personCode");
		userNameCol = new AnsarTableColumn<>("userName");
		userNameCol.config("userName");
		table.getColumns().addAll(codeCol, nameCol, creationDateCol, personTypeCol, personCodeCol, userNameCol);
		return table;
	}

	@Override
	public void selectRowAction(AnsarUser item) {
		code.insertValue(item.getCode());
		creationDate.insertValue(DateTimeUtility.formatDateTime(item.getCreationDate()));
		name.insertValue(item.getName());
		personType.insertValue(item.getUserDetails().getPersonType());
		personCode.insertValue(item.getUserDetails().getPersonCode());
		userName.insertValue(item.getUserName());
		password.insertValue(item.getPassword());
	}

	@Override
	public Class<AnsarUser> fetchDocumentClass() {
		return AnsarUser.class;
	}

	@Override
	public AnsarTable<AnsarUser> fetchTable() {
		return table;
	}

	@Override
	public AnsarLabeledControlHBox<String> fetchCodeBox() {
		return code;
	}

	@Override
	public AnsarLabeledControlHBox<String> fetchCreationDateBox() {
		return creationDate;
	}

	@Override
	public AnsarUser createEntity() {
		AnsarUser user = new AnsarUser();
		String code = this.code.fetchValue();
		Long id = table.getItems().stream().filter(i -> ObjectChecker.areEqual(i.getCode(), code)).findFirst()
				.map(AnsarBaseEntity::getId).orElse(null);
		user.setId(id);
		user.setCode(code);
		user.setCreationDate(DateTimeUtility.parseDateTime(creationDate.fetchValue()));
		user.setName(name.fetchValue());
		UserDetails userDetails = new UserDetails();
		userDetails.setPersonType(personType.fetchValue());
		userDetails.setPersonCode(Long
				.valueOf(ObjectChecker.isEmptyOrZeroOrNull(personCode.fetchValue()) ? "0" : personCode.fetchValue()));
		user.setUserDetails(userDetails);
		user.setUserName(userName.fetchValue());
		user.setPassword(password.fetchValue());
		return user;
	}
}
