package application;

import java.time.LocalDateTime;
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
import entities.GroupLevel;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import model.Persister;
import utilities.DateTimeUtility;
import utilities.ObjectChecker;
import utilities.ResourceUtility;
import utilities.Surah;

public class GroupLevelScreen implements IFileScreen<GroupLevel> {
	private AnsarScene scene;
	private AnsarLabeledControlHBox<String> codeBox;
	private AnsarLabeledControlHBox<String> nameBox;
	private AnsarLabeledControlHBox<String> creationDateBox;
	private AnsarLabeledControlHBox<Surah> fromSurah;
	private AnsarLabeledControlHBox<Surah> toSurah;
	private AnsarLabeledControlHBox<String> dailyRecitationInVerses;
	private AnsarLabeledControlHBox<Surah> revisionFromSurah;
	private AnsarLabeledControlHBox<Surah> revisionToSurah;
	private AnsarLabeledControlHBox<String> revisionRecitationInVerses;
	private AnsarButton saveBtn;
	private AnsarButton backBtn;
	private AnsarTable<GroupLevel> table;
	private AnsarTableColumn<GroupLevel, String> codeCol;
	private AnsarTableColumn<GroupLevel, String> nameCol;
	private AnsarTableColumn<GroupLevel, LocalDateTime> creationDateCol;
	private AnsarTableColumn<GroupLevel, Surah> fromSurahCol;
	private AnsarTableColumn<GroupLevel, Surah> toSurahCol;
	private AnsarTableColumn<GroupLevel, Integer> dailyRecitationInVersesCol;
	private AnsarTableColumn<GroupLevel, Surah> revisionFromSurahCol;
	private AnsarTableColumn<GroupLevel, Surah> revisionToSurahCol;
	private AnsarTableColumn<GroupLevel, Integer> revisionRecitationInVersesCol;

	public GroupLevelScreen() {
		this.scene = constructScreen();
	}

	@Override
	public Pane createHeaderBox() {
		AnsarGridPane headerPane = new AnsarGridPane();
		codeBox = new AnsarLabeledControlHBox<>("code", ControlType.Label);
		codeBox.insertValue(fetchCode());
		creationDateBox = new AnsarLabeledControlHBox<>("creationDate", ControlType.Label);
		creationDateBox.insertValue(DateTimeUtility.fetchFormatedCurrentDateTime());
		nameBox = new AnsarLabeledControlHBox<>("name", ControlType.TextField);
		fromSurah = new AnsarLabeledControlHBox<>("fromSurah", ControlType.ComboBox);
		AnsarComboBox<Surah> fromSurahComboBox = fromSurah.getControl();
		fromSurahComboBox.config(ResourceUtility.getSurahs(), s -> s.getName());
		toSurah = new AnsarLabeledControlHBox<>("toSurah", ControlType.ComboBox);
		AnsarComboBox<Surah> toSurahComboBox = toSurah.getControl();
		toSurahComboBox.config(ResourceUtility.getSurahs(), s -> s.getName());
		dailyRecitationInVerses = new AnsarLabeledControlHBox<>("dailyRecitationInVerses", ControlType.TextField);
		revisionFromSurah = new AnsarLabeledControlHBox<>("revisionFromSurah", ControlType.ComboBox);
		AnsarComboBox<Surah> revisionFromSurahComboBox = revisionFromSurah.getControl();
		revisionFromSurahComboBox.config(ResourceUtility.getSurahs(), s -> s.getName());
		revisionToSurah = new AnsarLabeledControlHBox<>("revisionToSurah", ControlType.ComboBox);
		AnsarComboBox<Surah> revisionToSurahComboBox = revisionToSurah.getControl();
		revisionToSurahComboBox.config(ResourceUtility.getSurahs(), s -> s.getName());
		revisionRecitationInVerses = new AnsarLabeledControlHBox<>("revisionRecitationInVerses", ControlType.TextField);
		headerPane.add(codeBox, 0, 0);
		headerPane.add(creationDateBox, 1, 0);
		headerPane.add(nameBox, 0, 1);
		headerPane.add(fromSurah, 0, 2);
		headerPane.add(toSurah, 1, 2);
		headerPane.add(dailyRecitationInVerses, 2, 2);
		headerPane.add(revisionFromSurah, 0, 3);
		headerPane.add(revisionToSurah, 1, 3);
		headerPane.add(revisionRecitationInVerses, 2, 3);
		return headerPane;
	}

	@Override
	public Pane createBtnsBox() {
		AnsarHBox btnsBox = new AnsarHBox();
		saveBtn = new AnsarButton("Save");
		saveBtn.setOnAction(e -> submit());
		backBtn = new AnsarButton("Back");
		backBtn.setOnAction(e -> ResourceUtility.fetchStage().setScene(HomeScreen.fetchScreen()));
		btnsBox.getChildren().addAll(saveBtn, backBtn);
		return btnsBox;
	}

	@Override
	public GroupLevel fetchFile() {
		Long code = Long.valueOf(codeBox.fetchValue());
		GroupLevel level = table.getItems().stream().filter(i -> ObjectChecker.areEqual(i.getCode(), code)).findFirst()
				.orElse(null);
		if (ObjectChecker.isEmptyOrNull(level)) {
			level = new GroupLevel();
			table.getItems().add(level);
		}
		level.setCode(code);
		level.setName(nameBox.fetchValue());
		level.setCreationDate(DateTimeUtility.parseDateTime(creationDateBox.fetchValue()));
		level.setFromSurah(fromSurah.fetchValue());
		level.setToSurah(toSurah.fetchValue());
		level.setDailyRecitationInVerses(Integer.valueOf(dailyRecitationInVerses.fetchValue()));
		level.setRevisionFromSurah(revisionFromSurah.fetchValue());
		level.setRevisionToSurah(revisionToSurah.fetchValue());
		level.setRevisionRecitationInVerses(Integer.valueOf(revisionRecitationInVerses.fetchValue()));
		return level;
	}

	@Override
	public void reset() {
		IFileScreen.super.reset();
		codeBox.insertValue(fetchCode());
		creationDateBox.insertValue(DateTimeUtility.fetchFormatedCurrentDateTime());
	}

	@Override
	public AnsarTable<GroupLevel> createTableView() {
		table = new AnsarTable<>();
		table.setRowFactory(c -> {
			AnsarTableRow<GroupLevel> row = new AnsarTableRow<>();
			row.setOnMouseClicked(e -> {
				AnsarTableRow<GroupLevel> rowRef = row;
				selectRowAction(rowRef.getItem());
			});
			return row;
		});
		codeCol = new AnsarTableColumn<>("code");
		codeCol.setCellValueFactory(new PropertyValueFactory<>("code"));
		nameCol = new AnsarTableColumn<>("name");
		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		creationDateCol = new AnsarTableColumn<>("creationDate");
		creationDateCol.config("creationDate");
		fromSurahCol = new AnsarTableColumn<>("fromSurah");
		fromSurahCol.config("fromSurah", s -> s.getName());
		toSurahCol = new AnsarTableColumn<>("toSurah");
		toSurahCol.config("toSurah", s -> s.getName());
		dailyRecitationInVersesCol = new AnsarTableColumn<>("dailyRecitationInVerses");
		dailyRecitationInVersesCol.config("dailyRecitationInVerses", v -> ObjectChecker.toString(v));
		revisionFromSurahCol = new AnsarTableColumn<>("revisionFromSurah");
		revisionFromSurahCol.config("revisionFromSurah", s -> s.getName());
		revisionToSurahCol = new AnsarTableColumn<>("revisionToSurah");
		revisionToSurahCol.config("revisionToSurah", s -> s.getName());
		revisionRecitationInVersesCol = new AnsarTableColumn<>("revisionRecitationInVerses");
		revisionRecitationInVersesCol.config("revisionRecitationInVerses", v -> ObjectChecker.toString(v));
		table.getColumns().addAll(codeCol, creationDateCol, nameCol, fromSurahCol, toSurahCol,
				dailyRecitationInVersesCol, revisionFromSurahCol, revisionToSurahCol, revisionRecitationInVersesCol);
		List<GroupLevel> groupLevels = Persister.list(GroupLevel.class);
		table.getItems().addAll(groupLevels);
		return table;
	}

	@Override
	public void selectRowAction(GroupLevel item) {
		codeBox.insertValue(item.getCode());
		creationDateBox.insertValue(DateTimeUtility.formatDateTime(item.getCreationDate()));
		nameBox.insertValue(item.getName());
		fromSurah.insertValue(item.getFromSurah());
		toSurah.insertValue(item.getToSurah());
		dailyRecitationInVerses.insertValue(item.getDailyRecitationInVerses());
		revisionFromSurah.insertValue(item.getRevisionFromSurah());
		revisionToSurah.insertValue(item.getRevisionToSurah());
		revisionRecitationInVerses.insertValue(item.getRevisionRecitationInVerses());
	}

	@Override
	public AnsarScene fetchScene() {
		return scene;
	}

	@Override
	public Class<?> fetchDocumentClass() {
		return GroupLevel.class;
	}

	@Override
	public AnsarScene refreshScreen() {
		reset();
		return scene;
	}

}
