package entities;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import utilities.ObjectChecker;
import utilities.ResourceUtility;
import utilities.Surah;

@Entity
public class GroupLevel extends AnsarBaseEntity {
	private Surah fromSurah;
	private Surah toSurah;
	private Integer dailyRecitationInVerses;
	private Surah revisionFromSurah;
	private Surah revisionToSurah;
	private Integer revisionRecitationInVerses;
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

	public Integer getDailyRecitationInVerses() {
		return dailyRecitationInVerses;
	}

	public void setDailyRecitationInVerses(Integer dailyRecitationInVerses) {
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

	public Integer getRevisionRecitationInVerses() {
		return revisionRecitationInVerses;
	}

	public void setRevisionRecitationInVerses(Integer revisionRecitationInVerses) {
		this.revisionRecitationInVerses = revisionRecitationInVerses;
	}

	@OneToMany(mappedBy = "groupLevel")
	public List<MemorizationGroup> getGroups() {
		return groups;
	}

	public void setGroups(List<MemorizationGroup> groups) {
		this.groups = groups;
	}
}
