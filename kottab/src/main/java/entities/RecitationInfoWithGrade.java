package entities;

import utilities.ObjectChecker;

import javax.persistence.*;

@Embeddable
public class RecitationInfoWithGrade extends AbsRecitationInfo
{
    private Byte grade;

    public Byte getGrade()
    {
        return grade;
    }

    public void setGrade(Byte grade)
    {
        this.grade = grade;
    }

    @Override
    @Transient
    public boolean isEmpty()
    {
        return super.isEmpty() || ObjectChecker.isEmptyOrZeroOrNull(grade);
    }

    //factory method very useful in this case
    public static RecitationInfoWithGrade fromRecitationInfo(RecitationInfo recitationInfo)
    {
        RecitationInfoWithGrade recitationInfoWithGrade = new RecitationInfoWithGrade();
        recitationInfoWithGrade.setFromSurah(recitationInfo.getFromSurah());
        recitationInfoWithGrade.setFromAya(recitationInfo.getFromAya());
        recitationInfoWithGrade.setToSurah(recitationInfo.getToSurah());
        recitationInfoWithGrade.setToAya(recitationInfo.getToAya());
        return recitationInfoWithGrade;
    }
}
