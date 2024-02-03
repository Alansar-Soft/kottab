package entities;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;

import model.Persister;
import utilities.ObjectChecker;
import utilities.ResourceUtility;
import utilities.Result;

@Entity
public class Student extends Person {
	private String parentsFirstPhoneNo;
	private String parentsSecondPhoneNo;
	private MemorizationGroup group;
	private List<RecitationEntry> recitationEntries;

	public String getParentsFirstPhoneNo() {
		return parentsFirstPhoneNo;
	}

	public void setParentsFirstPhoneNo(String parentsFirstPhoneNo) {
		this.parentsFirstPhoneNo = parentsFirstPhoneNo;
	}

	public String getParentsSecondPhoneNo() {
		return parentsSecondPhoneNo;
	}

	public void setParentsSecondPhoneNo(String parentsSecondPhoneNo) {
		this.parentsSecondPhoneNo = parentsSecondPhoneNo;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "group_id")
	public MemorizationGroup getGroup() {
		return group;
	}

	public void setGroup(MemorizationGroup group) {
		this.group = group;
	}

	@OneToMany(mappedBy = "student", cascade = CascadeType.REMOVE)
	public List<RecitationEntry> getRecitationEntries() {
		return recitationEntries;
	}

	public void setRecitationEntries(List<RecitationEntry> recitationEntries) {
		this.recitationEntries = recitationEntries;
	}

	@Transient
	public String getGroupName() {
		return ObjectChecker.isEmptyOrZeroOrNull(group) ? "" : ObjectChecker.toStringOrEmpty(group.getName());
	}

	@Override
	public Result postCommit() {
		Result result = new Result();
		if (Persister.countOf(RecitationEntry.class, " WHERE student_id = " + getId()) > 0)
			return result;
		RecitationEntry entry = new RecitationEntry();
		entry.setStudent(this);
		entry.setCreationDate(LocalDate.now());
		GroupLevel level = getGroup().getGroupLevel();

		RecitationInfo nextRecitation = new RecitationInfo();
		nextRecitation.setFromSurah(level.getFromSurah());
		nextRecitation.setFromAya(1);
		Integer dailyRecitationInVerses = level.getDailyRecitationInVerses().intValue() - 1;
		nextRecitation.setToSurah(ResourceUtility.calcSurah(nextRecitation.getFromSurah(),
				nextRecitation.getFromAya().intValue(), dailyRecitationInVerses));
		nextRecitation.setToAya(ResourceUtility.calcAya(nextRecitation.getFromSurah(),
				nextRecitation.getFromAya().intValue(), dailyRecitationInVerses));

		RecitationInfo nextRevision = new RecitationInfo();
		nextRevision.setFromSurah(level.getRevisionFromSurah());
		nextRevision.setFromAya(1);
		Integer revisionRecitationInVerses = level.getRevisionRecitationInVerses() - 1;
		nextRevision.setToSurah(ResourceUtility.calcSurah(nextRevision.getFromSurah(),
				nextRevision.getFromAya().intValue(), revisionRecitationInVerses));
		nextRevision.setToAya(ResourceUtility.calcAya(nextRevision.getFromSurah(), nextRevision.getFromAya().intValue(),
				revisionRecitationInVerses));

		entry.setNextRecitation(nextRecitation);
		entry.setNextRevision(nextRevision);
		entry.setRemark("from registration");

		result.accmulate(Persister.saveOrUpdate(entry));
		return result;
	}

	@Override
	@Transient
	public Result isValidForCommit() {
		Result result = super.isValidForCommit();
		LocalDate todayDate = LocalDate.now();
		if (getBirthdate() != null && todayDate.getYear() - getBirthdate().getYear() < 5)
			result.accmulate(
					Result.createFailureResult("Student should have at least 5 years. Please check birthdate"));
		validatePhoneNumber(parentsFirstPhoneNo, "Parents first", result);
		validatePhoneNumber(parentsSecondPhoneNo, "Parents second", result);
		if (getGroup() == null)
			result.accmulate(Result.createFailureResult("You must choose group"));
		return result;
	}

}
