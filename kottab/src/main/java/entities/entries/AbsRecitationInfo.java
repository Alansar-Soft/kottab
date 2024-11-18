package entities.entries;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import utilities.*;

@MappedSuperclass
public abstract class AbsRecitationInfo
{
    private Surah fromSurah;
    private Surah toSurah;
    private Short fromAya;
    private Short toAya;

    public Surah getFromSurah()
    {
        if (fromSurah != null && fromSurah.isEmpty())
            fromSurah.updateDataFrom(SurahsUtil.fetchSurah(fromSurah.getNumberOfSurah()));
        return fromSurah;
    }

    public void setFromSurah(Surah fromSurah)
    {
        this.fromSurah = fromSurah;
    }

    public Surah getToSurah()
    {
        if (toSurah != null && toSurah.isEmpty())
            toSurah.updateDataFrom(SurahsUtil.fetchSurah(toSurah.getNumberOfSurah()));
        return toSurah;
    }

    public void setToSurah(Surah toSurah)
    {
        this.toSurah = toSurah;
    }

    public Short getFromAya()
    {
        return fromAya;
    }

    public void setFromAya(Number fromAya)
    {
        this.fromAya = NumbersUtility.castToShort(fromAya);
    }

    public Short getToAya()
    {
        return toAya;
    }

    public void setToAya(Number toAya)
    {
        this.toAya = NumbersUtility.castToShort(toAya);
    }

    @Transient
    public boolean isEmpty()
    {
        return ObjectChecker.areAllEmptyOrNull(fromSurah, fromAya, toSurah, toAya);
    }
}
