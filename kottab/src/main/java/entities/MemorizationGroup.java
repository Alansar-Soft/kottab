package entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import utilities.Result;

@Entity
public class MemorizationGroup extends AnsarBaseEntity {
	private MemorizationTeacher teacher;
	private MemorizationTeacher assistantTeacher;
	private List<Student> students;
	private GroupLevel groupLevel;

	@ManyToOne
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "group")
	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "groupLevel_id")
	public GroupLevel getGroupLevel() {
		return groupLevel;
	}

	public void setGroupLevel(GroupLevel groupLevel) {
		this.groupLevel = groupLevel;
	}

	@Override
	@Transient
	public Result isValidForCommit() {
		Result result = super.isValidForCommit();
		if (getGroupLevel() == null)
			result.accmulate(Result.createFailureResult("You must choose group level"));
		if (getTeacher() == null)
			result.accmulate(Result.createFailureResult("You must choose teacher"));
		return result;
	}

}
