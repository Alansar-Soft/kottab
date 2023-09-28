package application;

import java.math.BigDecimal;

import ansarcontrols.AnsarButton;
import ansarcontrols.AnsarHBox;
import ansarcontrols.AnsarLabeledControlHBox;
import ansarcontrols.AnsarScene;
import ansarcontrols.AnsarTable;
import ansarcontrols.AnsarVBox;
import ansarcontrols.AnsarVBoxRoot;
import ansarcontrols.ControlType;
import entities.RecitationEntry;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import model.Persister;
import utilities.ObjectChecker;
import utilities.ResourceUtility;

public class RecitationNoteBookScreen implements IFileScreen<RecitationEntry> {
	private AnsarScene scene;
	private AnsarVBoxRoot root;
	private AnsarLabeledControlHBox studentId;
	private AnsarLabeledControlHBox studentName;
	private RecitationBox recitationBox;
	private RecitationBox revisionBox;
	private RecitationBox memorizationBox;
	private AnsarLabeledControlHBox remark;
	private AnsarButton saveBtn;
	private AnsarButton backBtn;

	public RecitationNoteBookScreen() {
		this.scene = constructScreen();
	}

	@Override
	public Pane createHeaderBox() {
		AnsarVBox headerPane = new AnsarVBox();
		studentId = new AnsarLabeledControlHBox("studentId", ControlType.TextField);
		studentId.setOnKeyPressed(e -> {
			if (ObjectChecker.areNotEqual(e.getCode(), KeyCode.ENTER))
				return;
			String id = ObjectChecker.toString(studentId.fetchValue());
			BigDecimal lastMemorizationPage = (BigDecimal) Persister
					.searchFor("SELECT entry.id FROM RecitationEntry entry "
							+ " JOIN Student std On entry.student_id = std.id " + " ORDER BY entry.creationDate DESC");
			String q = "SELECT level.id FROM Student std JOIN MemorizationGroup g ON std.group_id = g.id"
					+ " JOIN GroupLevel level ON g.groupLevel_id = level.id Where std.code = " + id;
			BigDecimal level = (BigDecimal) Persister.searchFor(q);
			System.out.println(ObjectChecker.isEmptyOrNull(lastMemorizationPage) ? "level is " + level
					: Persister.findById(RecitationEntry.class, lastMemorizationPage.longValue()).toString());
		});
		studentName = new AnsarLabeledControlHBox("studentName", ControlType.TextField);
		recitationBox = new RecitationBox("recitation");
		revisionBox = new RecitationBox("revision");
		memorizationBox = new RecitationBox("memorization");
		remark = new AnsarLabeledControlHBox("remark", ControlType.TextArea);
		headerPane.getChildren().addAll(studentId, studentName, recitationBox, revisionBox, memorizationBox, remark);
		return headerPane;
	}

	@Override
	public Pane createBtnsBox() {
		AnsarHBox btnsBox = new AnsarHBox();
		saveBtn = new AnsarButton("Save");
		backBtn = new AnsarButton("Back");
		backBtn.setOnAction(e -> ResourceUtility.fetchStage().setScene(HomeScreen.fetchScreen()));
		btnsBox.getChildren().addAll(saveBtn, backBtn);
		return btnsBox;
	}

	@Override
	public void reset() {

	}

	@Override
	public AnsarTable createTableView() {
		return new AnsarTable<>();
	}

	@Override
	public void selectRowAction(RecitationEntry item) {

	}

	@Override
	public AnsarScene fetchScene() {
		return scene;
	}

	@Override
	public Class<?> fetchDocumentClass() {
		return null;
	}

	@Override
	public RecitationEntry fetchFile() {
		return null;
	}
}
