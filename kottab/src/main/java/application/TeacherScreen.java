package application;

import ansarcontrols.AnsarScene;
import entities.MemorizationTeacher;
import entities.Person;

public class TeacherScreen extends AbsPersonScreen<MemorizationTeacher> {

	public TeacherScreen() {
		AnsarScene scene = constructScreen();
		setScene(scene);
	}

	@Override
	public String fetchScreenTitle() {
		return "teachersScreen";
	}

	@Override
	public Person createPersonEntity() {
		return new MemorizationTeacher();
	}

	@Override
	public Class<MemorizationTeacher> fetchDocumentClass() {
		return MemorizationTeacher.class;
	}

	@Override
	public void selectRowAction(MemorizationTeacher item) {
		super.selectRowDefaultAction(item);
	}
}
