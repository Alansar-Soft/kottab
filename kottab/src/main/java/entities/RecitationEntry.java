package entities;

import java.time.LocalDate;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class RecitationEntry {
	private Long id;
	private LocalDate creationDate;
	private RecitationInfoWithGrade recitation;
	private RecitationInfoWithGrade revision;
	private RecitationInfo memorization;
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
			@AttributeOverride(name = "fromSurah.numberOfSurah", column = @Column(name = "memorization_fromSurah")),
			@AttributeOverride(name = "toSurah.numberOfSurah", column = @Column(name = "memorization_toSurah")),
			@AttributeOverride(name = "fromAya", column = @Column(name = "memorization_fromAya")),
			@AttributeOverride(name = "toAya", column = @Column(name = "memorization_toAya")) })
	public RecitationInfo getMemorization() {
		return memorization;
	}

	public void setMemorization(RecitationInfo memorization) {
		this.memorization = memorization;
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

}
