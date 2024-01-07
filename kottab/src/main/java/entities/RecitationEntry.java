package entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import utilities.IFile;
import utilities.Result;

@Entity
public class RecitationEntry implements IFile {
	private Long id;
	private LocalDate creationDate;
	private RecitationInfoWithGrade recitation;
	private RecitationInfoWithGrade revision;
	private RecitationInfo nextRecitation;
	private RecitationInfo nextRevision;
	private String remark;
	private Student student;

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = creationDate;
	}

	@Embedded
	@AttributeOverrides(value = {
			@AttributeOverride(name = "fromSurah.numberOfSurah", column = @Column(name = "recitation_fromSurah")),
			@AttributeOverride(name = "toSurah.numberOfSurah", column = @Column(name = "recitation_toSurah")),
			@AttributeOverride(name = "fromAya", column = @Column(name = "recitation_fromAya")),
			@AttributeOverride(name = "toAya", column = @Column(name = "recitation_toAya")),
			@AttributeOverride(name = "grade", column = @Column(name = "recitation_grade")) })
	public RecitationInfoWithGrade getRecitation() {
		return recitation;
	}

	public void setRecitation(RecitationInfoWithGrade recitation) {
		this.recitation = recitation;
	}

	@Embedded
	@AttributeOverrides(value = {
			@AttributeOverride(name = "fromSurah.numberOfSurah", column = @Column(name = "revision_fromSurah")),
			@AttributeOverride(name = "toSurah.numberOfSurah", column = @Column(name = "revision_toSurah")),
			@AttributeOverride(name = "fromAya", column = @Column(name = "revision_fromAya")),
			@AttributeOverride(name = "toAya", column = @Column(name = "revision_toAya")),
			@AttributeOverride(name = "grade", column = @Column(name = "revision_grade")) })
	public RecitationInfoWithGrade getRevision() {
		return revision;
	}

	public void setRevision(RecitationInfoWithGrade revision) {
		this.revision = revision;
	}

	@Embedded
	@AttributeOverrides(value = {
			@AttributeOverride(name = "fromSurah.numberOfSurah", column = @Column(name = "nextRecitation_fromSurah")),
			@AttributeOverride(name = "toSurah.numberOfSurah", column = @Column(name = "nextRecitation_toSurah")),
			@AttributeOverride(name = "fromAya", column = @Column(name = "nextRecitation_fromAya")),
			@AttributeOverride(name = "toAya", column = @Column(name = "nextRecitation_toAya")) })
	public RecitationInfo getNextRecitation() {
		return nextRecitation;
	}

	public void setNextRecitation(RecitationInfo nextRecitation) {
		this.nextRecitation = nextRecitation;
	}

	@Embedded
	@AttributeOverrides(value = {
			@AttributeOverride(name = "fromSurah.numberOfSurah", column = @Column(name = "nextRevision_fromSurah")),
			@AttributeOverride(name = "toSurah.numberOfSurah", column = @Column(name = "nextRevision_toSurah")),
			@AttributeOverride(name = "fromAya", column = @Column(name = "nextRevision_fromAya")),
			@AttributeOverride(name = "toAya", column = @Column(name = "nextRevision_toAya")) })
	public RecitationInfo getNextRevision() {
		return nextRevision;
	}

	public void setNextRevision(RecitationInfo nextRevision) {
		this.nextRevision = nextRevision;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@ManyToOne
	@JoinColumn(name = "student_id")
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	@Override
	@Transient
	public Result isValidForCommit() {
		Result result = new Result();
		if (recitation != null && recitation.isEmpty())
			result.failure("You must enter all recitation data");
		if (revision != null && revision.isEmpty())
			result.failure("You must enter all revision data");
		if (nextRecitation != null && nextRecitation.isEmpty())
			result.failure("You must enter all next recitation data");
		if (nextRevision != null && nextRevision.isEmpty())
			result.failure("You must enter all next revision data");
		if (student == null)
			result.failure("You must enter student");
		return result;
	}

	@Override
	public Result postCommit() {
		return new Result();
	}

}
