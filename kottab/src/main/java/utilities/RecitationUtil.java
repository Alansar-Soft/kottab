package utilities;

import entities.*;

public class RecitationUtil
{
    public static RecitationInfo calcNextRecitationInfo(AbsRecitationInfo lastRecitationInfo, Integer versesCount)
    {
        RecitationInfo nextRecitation = new RecitationInfo();
        int lastRecitedAya = lastRecitationInfo.getToAya().intValue();
        nextRecitation.setFromSurah(SurahsUtil.calcSurah(lastRecitationInfo.getToSurah(), lastRecitedAya + 1, 0));
        nextRecitation.setFromAya(SurahsUtil.calcAya(lastRecitationInfo.getToSurah(), lastRecitedAya + 1, 0));
        nextRecitation.setToSurah(SurahsUtil.calcSurah(nextRecitation.getFromSurah(), lastRecitedAya, versesCount));
        nextRecitation.setToAya(SurahsUtil.calcAya(nextRecitation.getFromSurah(), lastRecitedAya, versesCount));
        return nextRecitation;
    }
}
