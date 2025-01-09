package entities.files;

import entities.details.*;
import utilities.*;

import javax.persistence.*;
import java.util.List;

import static utilities.ValidatorUtil.isEmptyRequired;

@Entity
public class GroupLevel extends AnsarFile
{
    private Surah fromSurah;
    private Surah toSurah;
    private Surah revisionFromSurah;
    private Surah revisionToSurah;
    private List<MemorizationGroup> groups;
    private List<GroupLevelDetail> details;
    private List<GroupLevelRevisionDetail> revisionDetails;

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

    @OneToMany(mappedBy = "groupLevel")
    public List<MemorizationGroup> getGroups()
    {
        return groups;
    }

    public void setGroups(List<MemorizationGroup> groups)
    {
        this.groups = groups;
    }

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "level_id")
    @OrderColumn(name = "line_number")
    public List<GroupLevelDetail> getDetails()
    {
        return details;
    }

    public void setDetails(List<GroupLevelDetail> details)
    {
        this.details = details;
    }

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "level_id")
    @OrderColumn(name = "line_number")
    public List<GroupLevelRevisionDetail> getRevisionDetails()
    {
        return revisionDetails;
    }

    public void setRevisionDetails(List<GroupLevelRevisionDetail> revisionDetails)
    {
        this.revisionDetails = revisionDetails;
    }

    @Override
    @Transient
    public Result isValidForCommit(Result result)
    {
        super.isValidForCommit(result);
        isEmptyRequired(fromSurah, "You must choose from surah", result);
        isEmptyRequired(toSurah, "You must choose to surah", result);
        isEmptyRequired(revisionFromSurah, "You must choose revision from surah", result);
        isEmptyRequired(revisionToSurah, "You must choose revision to surah", result);
        isEmptyRequired(details, "Details can not be empty", result);
        isEmptyRequired(revisionDetails, "Revision details can not be empty", result);
        return result;
    }
}
