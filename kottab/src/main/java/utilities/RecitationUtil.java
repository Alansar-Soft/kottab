package utilities;

import entities.entries.*;
import entities.files.Student;
import model.Persister;

public class RecitationUtil
{
    public static RecitationInfo calcNextRecitationInfo(AbsRecitationInfo lastRecitationInfo, Number versesCount)
    {
        RecitationInfo nextRecitation = new RecitationInfo();
        int lastRecitedAya = lastRecitationInfo.getToAya().intValue();
        nextRecitation.setFromSurah(SurahsUtil.calcSurah(lastRecitationInfo.getToSurah(), lastRecitedAya + 1, 0));
        nextRecitation.setFromAya(SurahsUtil.calcAya(lastRecitationInfo.getToSurah(), lastRecitedAya + 1, 0));
        nextRecitation.setToSurah(SurahsUtil.calcSurah(nextRecitation.getFromSurah(), lastRecitedAya, versesCount.intValue()));
        nextRecitation.setToAya(SurahsUtil.calcAya(nextRecitation.getFromSurah(), lastRecitedAya, versesCount.intValue()));
        return nextRecitation;
    }

    //factory method very useful in this case remove duplicated code
    public static RecitationEntry createRecitationEntryForStudent(Student student)
    {
        RecitationEntry lastEntry = Persister.getSingleResult(
                "FROM RecitationEntry WHERE student_id = :studentId ORDER BY creationDate DESC, creationTime DESC",
                Persister.params("studentId", student.getId()));
        RecitationEntry entry = new RecitationEntry();
        entry.setStudent(student);
        entry.setGroup(student.getGroup());
        entry.setRecitation(RecitationInfoWithGrade.fromRecitationInfo(lastEntry.getNextRecitation()));
        entry.setRevision(RecitationInfoWithGrade.fromRecitationInfo(lastEntry.getNextRevision()));
        entry.setNextRecitation(calcNextRecitationInfo(entry.getRecitation(), student.getGroup().getGroupLevel().getDailyRecitationInVerses()));
        entry.setNextRevision(calcNextRecitationInfo(entry.getRevision(),
                student.getGroup().getGroupLevel().getRevisionRecitationInVerses()));
        return entry;
    }
}
