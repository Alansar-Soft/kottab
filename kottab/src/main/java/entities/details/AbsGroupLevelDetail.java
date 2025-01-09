package entities.details;

import utilities.Surah;

import javax.persistence.*;

@MappedSuperclass
public abstract class AbsGroupLevelDetail
{
    private Long id;
    private int sessionNo;
    private Surah fromSurah;
    private int fromAya;
    private Surah toSurah;
    private int toAya;

    @Id
    @GeneratedValue
    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public int getSessionNo()
    {
        return sessionNo;
    }

    public void setSessionNo(int sessionNo)
    {
        this.sessionNo = sessionNo;
    }

    @Embedded
    @AttributeOverride(name = "numberOfSurah", column = @Column(name = "fromSurah"))
    public Surah getFromSurah()
    {
        return fromSurah;
    }

    public void setFromSurah(Surah fromSurah)
    {
        this.fromSurah = fromSurah;
    }

    public int getFromAya()
    {
        return fromAya;
    }

    public void setFromAya(int fromAya)
    {
        this.fromAya = fromAya;
    }

    @Embedded
    @AttributeOverride(name = "numberOfSurah", column = @Column(name = "toSurah"))
    public Surah getToSurah()
    {
        return toSurah;
    }

    public void setToSurah(Surah toSurah)
    {
        this.toSurah = toSurah;
    }

    public int getToAya()
    {
        return toAya;
    }

    public void setToAya(int toAya)
    {
        this.toAya = toAya;
    }
}
