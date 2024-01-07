package entities;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import utilities.NumbersUtility;
import utilities.ObjectChecker;
import utilities.ResourceUtility;
import utilities.Result;
import utilities.Surah;

@Entity
public class GroupLevel extends AnsarBaseEntity {
	private Surah fromSurah;
	private Surah toSurah;
	private Short dailyRecitationInVerses;
	private Surah revisionFromSurah;
	private Surah revisionToSurah;
	private Short revisionRecitationInVerses;
	private List<MemorizationGroup> groups;

	@Override
	@Column(unique = true)
	public Long getCode() {
		return super.getCode();
	}

	@Embedded
	@AttributeOverride(name = "numberOfSurah", column = @Column(name = "fromSurah"))
	public Surah getFromSurah() {
		return ResourceUtility.getSurahs().get(ObjectChecker.toZeroIfNull(fromSurah.getNumberOfSurah()));
	}

	public void setFromSurah(Surah fromSurah) {
		this.fromSurah = fromSurah;
	}

	@Embedded
	@AttributeOverride(name = "numberOfSurah", column = @Column(name = "toSurah"))
	public Surah getToSurah() {
		return ResourceUtility.getSurahs().get(ObjectChecker.toZeroIfNull(toSurah.getNumberOfSurah()));
	}

	public void setToSurah(Surah toSurah) {
		this.toSurah = toSurah;
	}

	public Short getDailyRecitationInVerses() {
		if (dailyRecitationInVerses == null)
			dailyRecitationInVerses = NumbersUtility.castToShort(0);
		return dailyRecitationInVerses;
	}

	public void setDailyRecitationInVerses(Short dailyRecitationInVerses) {
		this.dailyRecitationInVerses = dailyRecitationInVerses;
	}

	@Embedded
	@AttributeOverride(name = "numberOfSurah", column = @Column(name = "revisionFromSurah"))
	public Surah getRevisionFromSurah() {
		return ResourceUtility.getSurahs().get(ObjectChecker.toZeroIfNull(revisionFromSurah.getNumberOfSurah()));
	}

	public void setRevisionFromSurah(Surah revisionFromSurah) {
		this.revisionFromSurah = revisionFromSurah;
	}

	@Embedded
	@AttributeOverride(name = "numberOfSurah", column = @Column(name = "revisionToSurah"))
	public Surah getRevisionToSurah() {
		return ResourceUtility.getSurahs().get(ObjectChecker.toZeroIfNull(revisionToSurah.getNumberOfSurah()));
	}

	public void setRevisionToSurah(Surah revisionToSurah) {
		this.revisionToSurah = revisionToSurah;
	}

	public Short getRevisionRecitationInVerses() {
		if (revisionRecitationInVerses == null)
			revisionRecitationInVerses = NumbersUtility.castToShort(0);
		return revisionRecitationInVerses;
	}

	public void setRevisionRecitationInVerses(Short revisionRecitationInVerses) {
		this.revisionRecitationInVerses = revisionRecitationInVerses;
	}

	@OneToMany(mappedBy = "groupLevel")
	public List<MemorizationGroup> getGroups() {
		return groups;
	}

	public void setGroups(List<MemorizationGroup> groups) {
		this.groups = groups;
	}

	@Override
	@Transient
	public Result isValidForCommit() {
		Result result = super.isValidForCommit();
		checkIfRequired(fromSurah, "You must choose from surah", result);
		checkIfRequired(toSurah, "You must choose to surah", result);
		checkIfRequired(dailyRecitationInVerses, "You must enter count of verses of recitation", result);
		checkIfRequired(revisionFromSurah, "You must choose revision from surah", result);
		checkIfRequired(revisionToSurah, "You must choose revision to surah", result);
		checkIfRequired(revisionRecitationInVerses, "You must enter count of verses of revision", result);
		return result;
	}

	public void checkIfRequired(Object field, String message, Result result) {
		if (ObjectChecker.isEmptyOrZeroOrNull(field))
			result.accmulate(Result.createFailureResult(message));
	}
}
