package entities;

import javax.persistence.MappedSuperclass;

import utilities.Surah;

@MappedSuperclass
public abstract class AbsRecitationInfo {
	private Surah fromSurah;
	private Surah toSurah;
	private Short fromAya;
	private Short toAya;

	public Surah getFromSurah() {
		return fromSurah;
	}

	public void setFromSurah(Surah fromSurah) {
		this.fromSurah = fromSurah;
	}

	public Surah getToSurah() {
		return toSurah;
	}

	public void setToSurah(Surah toSurah) {
		this.toSurah = toSurah;
	}

	public Short getFromAya() {
		return fromAya;
	}

	public void setFromAya(Short fromAya) {
		this.fromAya = fromAya;
	}

	public Short getToAya() {
		return toAya;
	}

	public void setToAya(Short toAya) {
		this.toAya = toAya;
	}

}
