package utilities;

import com.fasterxml.jackson.annotation.JsonGetter;

import javax.persistence.*;

@Embeddable
public class Surah
{
    private String name;
    private byte numberOfSurah;
    private short versesCount;

    public Surah()
    {

    }

    public Surah(String name, int numberOfSurah, int versesCount)
    {
        this.name = name;
        this.numberOfSurah = (byte) numberOfSurah;
        this.versesCount = (short) versesCount;
    }

    @Transient
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public byte getNumberOfSurah()
    {
        return numberOfSurah;
    }

    public void setNumberOfSurah(byte numberOfSurah)
    {
        this.numberOfSurah = numberOfSurah;
    }

    @Transient
    public short getVersesCount()
    {
        return versesCount;
    }

    public void setVersesCount(short versesCount)
    {
        this.versesCount = versesCount;
    }

    @Transient
    public boolean isEmpty()
    {
        return ObjectChecker.areAllEmptyOrNull(getName(), getVersesCount());
    }

    public void updateDataFrom(Surah surah)
    {
        setName(surah.getName());
        setNumberOfSurah(surah.getNumberOfSurah());
        setVersesCount(surah.getVersesCount());
    }

    @JsonGetter("name")
    public String nameByLang()
    {
        return Translator.translate(name);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
            return false;
        return ObjectChecker.areEqual(numberOfSurah, ((Surah) obj).getNumberOfSurah());
    }

    @Override
    public int hashCode()
    {
        return numberOfSurah;
    }
}
