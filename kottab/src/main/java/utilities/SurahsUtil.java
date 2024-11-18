package utilities;

import entities.files.GroupLevel;

import java.util.*;

public class SurahsUtil
{
    private static List<Surah> surahs;

    public static List<Surah> getSurahs()
    {
        if (ObjectChecker.isNotEmptyOrZeroOrNull(surahs))
            return surahs;
        surahs = new ArrayList<>();
        surahs.add(new Surah("", 0, 0));
        surahs.add(new Surah("Al-Fatihah", 1, 7));
        surahs.add(new Surah("Al-Baqarah", 2, 286));
        surahs.add(new Surah("Al-'Imran", 3, 200));
        surahs.add(new Surah("An-Nisa'", 4, 176));
        surahs.add(new Surah("Al-Ma'idah", 5, 120));
        surahs.add(new Surah("Al-An'am", 6, 165));
        surahs.add(new Surah("Al-A'raf", 7, 206));
        surahs.add(new Surah("Al-Anfal", 8, 75));
        surahs.add(new Surah("At-Taubah", 9, 129));
        surahs.add(new Surah("Yunus", 10, 109));
        surahs.add(new Surah("Hud", 11, 123));
        surahs.add(new Surah("Yusuf", 12, 111));
        surahs.add(new Surah("Ar-Ra'd", 13, 43));
        surahs.add(new Surah("Ibrahim", 14, 52));
        surahs.add(new Surah("Al-Hijr", 15, 99));
        surahs.add(new Surah("An-Nahl", 16, 128));
        surahs.add(new Surah("Al-Isra", 17, 111));
        surahs.add(new Surah("Al-Kahf", 18, 110));
        surahs.add(new Surah("Maryam", 19, 98));
        surahs.add(new Surah("Ta Ha", 20, 135));
        surahs.add(new Surah("Al-Anbiya'", 21, 112));
        surahs.add(new Surah("Al-Hajj", 22, 78));
        surahs.add(new Surah("Al-Mu'minun", 23, 118));
        surahs.add(new Surah("An-Nur", 24, 64));
        surahs.add(new Surah("Al-Furqan", 25, 77));
        surahs.add(new Surah("Ash-Shu'ara'", 26, 227));
        surahs.add(new Surah("An-Naml", 27, 93));
        surahs.add(new Surah("Al-Qasas", 28, 88));
        surahs.add(new Surah("Al-'Ankabut", 29, 69));
        surahs.add(new Surah("Ar-Rum", 30, 60));
        surahs.add(new Surah("Luqman", 31, 34));
        surahs.add(new Surah("As-Sajdah", 32, 30));
        surahs.add(new Surah("Al-Ahzab", 33, 73));
        surahs.add(new Surah("Saba'", 34, 54));
        surahs.add(new Surah("Fatir", 35, 45));
        surahs.add(new Surah("Ya Sin", 36, 83));
        surahs.add(new Surah("As-Saffat", 37, 181));
        surahs.add(new Surah("sad", 38, 88));
        surahs.add(new Surah("Az-Zumar", 39, 75));
        surahs.add(new Surah("Ghafir", 40, 85));
        surahs.add(new Surah("Fussilat", 41, 54));
        surahs.add(new Surah("Ash-Shura", 42, 53));
        surahs.add(new Surah("Az-Zukhruf", 43, 89));
        surahs.add(new Surah("Ad-Dukhan", 44, 59));
        surahs.add(new Surah("Al-Jathiyah", 45, 37));
        surahs.add(new Surah("Al-Ahqaf", 46, 35));
        surahs.add(new Surah("Muhammad", 47, 38));
        surahs.add(new Surah("Al-Fath", 48, 29));
        surahs.add(new Surah("Al-Hujurat", 49, 18));
        surahs.add(new Surah("Qaf", 50, 45));
        surahs.add(new Surah("Ad-Dhariyat", 51, 60));
        surahs.add(new Surah("At-Tur", 52, 49));
        surahs.add(new Surah("An-Najm", 53, 62));
        surahs.add(new Surah("Al-Qamar", 54, 55));
        surahs.add(new Surah("Ar-Rahman", 55, 78));
        surahs.add(new Surah("Al-Waqi'ah", 56, 96));
        surahs.add(new Surah("Al-Hadid", 57, 29));
        surahs.add(new Surah("Al-Mujadilah", 58, 22));
        surahs.add(new Surah("Al-Hashr", 59, 24));
        surahs.add(new Surah("Al-Mumtahanah", 60, 13));
        surahs.add(new Surah("As-Saff", 61, 14));
        surahs.add(new Surah("Al-Jumu'ah", 62, 11));
        surahs.add(new Surah("Al-Munafiqun", 63, 11));
        surahs.add(new Surah("At-Taghabun", 64, 18));
        surahs.add(new Surah("At-Talaq", 65, 12));
        surahs.add(new Surah("At-Tahrim", 66, 12));
        surahs.add(new Surah("Al-Mulk", 67, 30));
        surahs.add(new Surah("Al-Qalam", 68, 52));
        surahs.add(new Surah("Al-Haqqah", 69, 52));
        surahs.add(new Surah("Al-Ma'arij", 70, 44));
        surahs.add(new Surah("Nuh", 71, 28));
        surahs.add(new Surah("Al-Jinn", 72, 28));
        surahs.add(new Surah("Al-Muzzammil", 73, 20));
        surahs.add(new Surah("Al-Muddaththir", 74, 56));
        surahs.add(new Surah("Al-Qiyamah", 75, 40));
        surahs.add(new Surah("Al-Insan", 76, 31));
        surahs.add(new Surah("Al-Mursalat", 77, 50));
        surahs.add(new Surah("An-Naba", 78, 40));
        surahs.add(new Surah("An-Nazi'at", 79, 46));
        surahs.add(new Surah("'Abasa", 80, 42));
        surahs.add(new Surah("At-Takwir", 81, 29));
        surahs.add(new Surah("Al-Infitar", 82, 19));
        surahs.add(new Surah("Al-mutaffifin", 83, 36));
        surahs.add(new Surah("Al-Inshiqaq", 84, 25));
        surahs.add(new Surah("Al-Buruj", 85, 22));
        surahs.add(new Surah("At-Tariq", 86, 17));
        surahs.add(new Surah("Al-A'la", 87, 19));
        surahs.add(new Surah("Al-Ghashiyah", 88, 26));
        surahs.add(new Surah("Al-Fajr", 89, 30));
        surahs.add(new Surah("Al-Balad", 90, 20));
        surahs.add(new Surah("Ash-Shams", 91, 15));
        surahs.add(new Surah("Al-Lail", 92, 21));
        surahs.add(new Surah("Ad-Duha", 93, 11));
        surahs.add(new Surah("Ash-Sharh", 94, 8));
        surahs.add(new Surah("At-Tin", 95, 8));
        surahs.add(new Surah("Al-'Alaq", 96, 19));
        surahs.add(new Surah("Al-Qadr", 97, 5));
        surahs.add(new Surah("Al-Bayyinah", 98, 8));
        surahs.add(new Surah("Al-Zilzal", 99, 8));
        surahs.add(new Surah("Al-'Adiyat", 100, 11));
        surahs.add(new Surah("Al-Qari'ah", 101, 11));
        surahs.add(new Surah("At-Takathur", 102, 8));
        surahs.add(new Surah("Al-'Asr", 103, 3));
        surahs.add(new Surah("Al-Humazah", 103, 9));
        surahs.add(new Surah("Al-Fil", 105, 5));
        surahs.add(new Surah("Quraish", 106, 4));
        surahs.add(new Surah("Al-Ma'un", 107, 7));
        surahs.add(new Surah("Al-Kauthar", 108, 3));
        surahs.add(new Surah("Al-Kafirun", 109, 6));
        surahs.add(new Surah("An-Nasr", 110, 3));
        surahs.add(new Surah("Al-Masad", 111, 5));
        surahs.add(new Surah("Al-Ikhlas", 112, 4));
        surahs.add(new Surah("Al-Falaq", 113, 5));
        surahs.add(new Surah("An-Nas", 114, 6));

        return surahs;
    }

    public static Surah fetchSurah(byte numberOfSurah)
    {
        return getSurahs().get(numberOfSurah);
    }

    public static Surah fetchSurah(String surahName)
    {
        return getSurahs().stream()
                .filter(s -> ObjectChecker.areEqual(Translator.translate(s.getName()), surahName)).findFirst()
                .orElse(null);
    }

    public static Surah calcSurah(Surah fromSurah, Integer fromAya, Integer versesCount)
    {

        if (fromAya == null)
            fromAya = 0;
        if (fromAya + versesCount < fromSurah.getVersesCount())
            return fromSurah;
        return fetchSurah(NumbersUtility.castToByte((fromSurah.getNumberOfSurah() + 1)));
    }

    public static Short calcAya(Surah fromSurah, Integer fromAya, Integer versesCount)
    {

        if (fromAya == null)
            fromAya = 0;

        int toAya = fromAya + versesCount;
        short surahVersesCount = fromSurah.getVersesCount();
        if (toAya <= surahVersesCount)
            return NumbersUtility.castToShort(toAya);
        return NumbersUtility.castToShort(toAya - surahVersesCount);
    }


    public static List<Surah> fetchRecitationSurahsOfLevel(GroupLevel groupLevel)
    {
        return surahs.subList(groupLevel.getFromSurah().getNumberOfSurah(),
                groupLevel.getToSurah().getNumberOfSurah());
    }

    public static List<Surah> fetchRevisionSurahsOfLevel(GroupLevel groupLevel)
    {
        return surahs.subList(groupLevel.getRevisionFromSurah().getNumberOfSurah(),
                groupLevel.getRevisionToSurah().getNumberOfSurah());
    }

    public static String fetchSurahName(byte b)
    {
        return fetchSurah(b).nameByLang();
    }
}
