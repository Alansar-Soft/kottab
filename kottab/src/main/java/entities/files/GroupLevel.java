package entities.files;

import utilities.*;

import javax.persistence.*;
import java.util.List;

import static utilities.ValidatorUtil.isEmptyRequired;

@Entity
public class GroupLevel extends AnsarFile
{
    private Surah fromSurah;
    private Surah toSurah;
    private Short dailyRecitationInVerses;
    private Surah revisionFromSurah;
    private Surah revisionToSurah;
    private Short revisionRecitationInVerses;
    private List<MemorizationGroup> groups;

    @Embedded
    @AttributeOverride(name = "numberOfSurah", column = @Column(name = "fromSurah"))
    public Surah getFromSurah()
    {
        return SurahsUtil.getSurahs().get(fromSurah.getNumberOfSurah());
    }

    public void setFromSurah(Surah fromSurah)
    {
        this.fromSurah = fromSurah;
    }

    @Embedded
    @AttributeOverride(name = "numberOfSurah", column = @Column(name = "toSurah"))
    public Surah getToSurah()
    {
        return SurahsUtil.getSurahs().get(toSurah.getNumberOfSurah());
    }

    public void setToSurah(Surah toSurah)
    {
        this.toSurah = toSurah;
    }

    public Short getDailyRecitationInVerses()
    {
        if (dailyRecitationInVerses == null)
            dailyRecitationInVerses = NumbersUtility.castToShort(0);
        return dailyRecitationInVerses;
    }

    public void setDailyRecitationInVerses(Short dailyRecitationInVerses)
    {
        this.dailyRecitationInVerses = dailyRecitationInVerses;
    }

    @Embedded
    @AttributeOverride(name = "numberOfSurah", column = @Column(name = "revisionFromSurah"))
    public Surah getRevisionFromSurah()
    {
        return SurahsUtil.getSurahs().get(revisionFromSurah.getNumberOfSurah());
    }

    public void setRevisionFromSurah(Surah revisionFromSurah)
    {
        this.revisionFromSurah = revisionFromSurah;
    }

    @Embedded
    @AttributeOverride(name = "numberOfSurah", column = @Column(name = "revisionToSurah"))
    public Surah getRevisionToSurah()
    {
        return SurahsUtil.getSurahs().get(revisionToSurah.getNumberOfSurah());
    }

    public void setRevisionToSurah(Surah revisionToSurah)
    {
        this.revisionToSurah = revisionToSurah;
    }

    public Short getRevisionRecitationInVerses()
    {
        if (revisionRecitationInVerses == null)
            revisionRecitationInVerses = NumbersUtility.castToShort(0);
        return revisionRecitationInVerses;
    }

    public void setRevisionRecitationInVerses(Short revisionRecitationInVerses)
    {
        this.revisionRecitationInVerses = revisionRecitationInVerses;
    }

    @OneToMany(mappedBy = "groupLevel")
    public List<MemorizationGroup> getGroups()
    {
        return groups;
    }

    public void setGroups(List<MemorizationGroup> groups)
    {
        this.groups = groups;
    }

    @Override
    @Transient
    public Result isValidForCommit(Result result)
    {
        super.isValidForCommit(result);
        isEmptyRequired(fromSurah, "You must choose from surah", result);
        isEmptyRequired(toSurah, "You must choose to surah", result);
        isEmptyRequired(dailyRecitationInVerses, "You must enter count of verses of recitation", result);
        isEmptyRequired(revisionFromSurah, "You must choose revision from surah", result);
        isEmptyRequired(revisionToSurah, "You must choose revision to surah", result);
        isEmptyRequired(revisionRecitationInVerses, "You must enter count of verses of revision", result);
        return result;
    }
}
