package application;

import ansarcontrols.AnsarScene;
import entities.MemorizationTeacher;
import entities.Person;

public class TeacherScreen<T extends MemorizationTeacher> extends AbsPersonScreen<T> {

	public TeacherScreen() {
		AnsarScene scene = constructScreen();
		setScene(scene);
	}

	@Override
	public Person createEntity() {
		return new MemorizationTeacher();
	}

	@Override
	public Class<MemorizationTeacher> fetchDocumentClass() {
		return MemorizationTeacher.class;
	}

	@Override
	public void selectRowAction(T item) {
		super.selectRowDefaultAction(item);
	}
}
