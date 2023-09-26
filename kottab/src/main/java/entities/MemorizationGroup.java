package entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class MemorizationGroup extends AnsarBaseEntity {
	private MemorizationTeacher teacher;
	private MemorizationTeacher assistantTeacher;
	private List<Student> students;
	private GroupLevel groupLevel;

	@OneToOne
	public MemorizationTeacher getTeacher() {
		return teacher;
	}

	public void setTeacher(MemorizationTeacher teacher) {
		this.teacher = teacher;
	}

	@OneToOne
	public MemorizationTeacher getAssistantTeacher() {
		return assistantTeacher;
	}

	public void setAssistantTeacher(MemorizationTeacher assistantTeacher) {
		this.assistantTeacher = assistantTeacher;
	}

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY, mappedBy = "group")
	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	@ManyToOne
	@JoinColumn(name = "groupLevel_id")
	public GroupLevel getGroupLevel() {
		return groupLevel;
	}

	public void setGroupLevel(GroupLevel groupLevel) {
		this.groupLevel = groupLevel;
	}

}
