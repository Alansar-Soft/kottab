package entities;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Nationalized;

import utilities.ObjectChecker;
import utilities.ResourceUtility;
import utilities.Surah;

@Entity
public class GroupLevel {
	private Long id;
	private Long code;
	private LocalDateTime creationDate;
	private String name;
	private Surah fromSurah;
	private Surah toSurah;
	private Integer dailyRecitationInVerses;
	private Surah revisionFromSurah;
	private Surah revisionToSurah;
	private Integer revisionRecitationInVerses;
	private List<MemorizationGroup> groups;

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(unique = true)
	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	@Nationalized
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
