package utilities;

import javax.persistence.Embeddable;
import javax.persistence.Transient;

@Embeddable
public class Surah {
	private String name;
	private byte numberOfSurah;
	private short versesCount;

	public Surah() {

	}

	public Surah(String name, int numberOfSurah, int versesCount) {
		this.name = name;
		this.numberOfSurah = (byte) numberOfSurah;
		this.versesCount = (short) versesCount;
	}

	@Transient
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte getNumberOfSurah() {
		return numberOfSurah;
	}

	public void setNumberOfSurah(byte numberOfSurah) {
		this.numberOfSurah = numberOfSurah;
	}

	@Transient
	public short getVersesCount() {
		return versesCount;
	}

	public void setVersesCount(short versesCount) {
		this.versesCount = versesCount;
	}

	@Transient
	public boolean isEmpty() {
		return ObjectChecker.areAllEmptyOrNull(getName(), getVersesCount());
	}

	public void updateDataFrom(Surah surah) {
		setName(surah.getName());
		setNumberOfSurah(surah.getNumberOfSurah());
		setVersesCount(surah.getVersesCount());
	}

	public String nameByLang() {
		return ResourceUtility.translate(name);
	}

}
