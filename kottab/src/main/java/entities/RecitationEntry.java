package entities;

import org.hibernate.annotations.Nationalized;
import utilities.Result;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
public class RecitationEntry extends StudentRelatedEntry
{
    private LocalTime creationTime;
    private RecitationInfoWithGrade recitation;
    private RecitationInfoWithGrade revision;
    private RecitationInfo nextRecitation;
    private RecitationInfo nextRevision;
    private String remark;

    public RecitationEntry()
    {
        super();
        creationTime = LocalTime.now();
    }

    public LocalTime getCreationTime()
    {
        return creationTime;
    }

    public void setCreationTime(LocalTime creationTime)
    {
        this.creationTime = creationTime;
    }

    @Embedded
    @AttributeOverrides(value = {@AttributeOverride(name = "fromSurah.numberOfSurah", column = @Column(name = "recitation_fromSurah")), @AttributeOverride(name = "toSurah.numberOfSurah", column = @Column(name = "recitation_toSurah")), @AttributeOverride(name = "fromAya", column = @Column(name = "recitation_fromAya")), @AttributeOverride(name = "toAya", column = @Column(name = "recitation_toAya")), @AttributeOverride(name = "grade", column = @Column(name = "recitation_grade"))})
    public RecitationInfoWithGrade getRecitation()
    {
        return recitation;
    }

    public void setRecitation(RecitationInfoWithGrade recitation)
    {
        this.recitation = recitation;
    }

    @Embedded
    @AttributeOverrides(value = {@AttributeOverride(name = "fromSurah.numberOfSurah", column = @Column(name = "revision_fromSurah")), @AttributeOverride(name = "toSurah.numberOfSurah", column = @Column(name = "revision_toSurah")), @AttributeOverride(name = "fromAya", column = @Column(name = "revision_fromAya")), @AttributeOverride(name = "toAya", column = @Column(name = "revision_toAya")), @AttributeOverride(name = "grade", column = @Column(name = "revision_grade"))})
    public RecitationInfoWithGrade getRevision()
    {
        return revision;
    }

    public void setRevision(RecitationInfoWithGrade revision)
    {
        this.revision = revision;
    }

    @Embedded
    @AttributeOverrides(value = {@AttributeOverride(name = "fromSurah.numberOfSurah", column = @Column(name = "nextRecitation_fromSurah")), @AttributeOverride(name = "toSurah.numberOfSurah", column = @Column(name = "nextRecitation_toSurah")), @AttributeOverride(name = "fromAya", column = @Column(name = "nextRecitation_fromAya")), @AttributeOverride(name = "toAya", column = @Column(name = "nextRecitation_toAya"))})
    public RecitationInfo getNextRecitation()
    {
        return nextRecitation;
    }

    public void setNextRecitation(RecitationInfo nextRecitation)
    {
        this.nextRecitation = nextRecitation;
    }

    @Embedded
    @AttributeOverrides(value = {@AttributeOverride(name = "fromSurah.numberOfSurah", column = @Column(name = "nextRevision_fromSurah")), @AttributeOverride(name = "toSurah.numberOfSurah", column = @Column(name = "nextRevision_toSurah")), @AttributeOverride(name = "fromAya", column = @Column(name = "nextRevision_fromAya")), @AttributeOverride(name = "toAya", column = @Column(name = "nextRevision_toAya"))})
    public RecitationInfo getNextRevision()
    {
        return nextRevision;
    }

    public void setNextRevision(RecitationInfo nextRevision)
    {
        this.nextRevision = nextRevision;
    }

    @Nationalized
    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    @Override
    @Transient
    public Result isValidForCommit()
    {
        Result result = super.isValidForCommit();
        if (recitation != null && recitation.isEmpty()) result.failure("You must enter all recitation data");
        if (revision != null && revision.isEmpty()) result.failure("You must enter all revision data");
        if (nextRecitation != null && nextRecitation.isEmpty())
            result.failure("You must enter all next recitation data");
        if (nextRevision != null && nextRevision.isEmpty()) result.failure("You must enter all next revision data");
        return result;
    }

    @Override
    public Result postCommit()
    {
        return new Result();
    }

}
