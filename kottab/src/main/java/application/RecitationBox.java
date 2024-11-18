package application;

import java.util.*;
import java.util.function.Consumer;

import ansarcontrols.*;
import entities.entries.*;
import utilities.*;

public class RecitationBox extends AnsarVBox
{
    private AnsarLabel title;
    private AnsarLabeledControlHBox<Surah> fromSurah;
    private AnsarLabeledControlHBox<Surah> toSurah;
    private AnsarLabeledControlHBox<String> fromAya;
    private AnsarLabeledControlHBox<String> toAya;

    public RecitationBox(String title)
    {
        this.title = new AnsarLabel(title);
        fromSurah = new AnsarLabeledControlHBox<>("fromSurah", ControlType.ComboBox);
        ((AnsarComboBox<Surah>) fromSurah.getControl()).config(SurahsUtil.getSurahs(), Surah::nameByLang);
        fromAya = new AnsarLabeledControlHBox<>("fromAya", ControlType.TextField);
        ((AnsarTextField) fromAya.getControl()).setEditable(false);
        toSurah = new AnsarLabeledControlHBox<>("toSurah", ControlType.ComboBox);
        ((AnsarComboBox<Surah>) toSurah.getControl()).config(SurahsUtil.getSurahs(), Surah::nameByLang);
        toAya = new AnsarLabeledControlHBox<>("toAya", ControlType.TextField);
        getChildren().addAll(Arrays.asList(new AnsarHBox(this.title), new AnsarHBox(fromSurah, fromAya),
                new AnsarHBox(toSurah, toAya)));
    }

    public Surah getFromSurah()
    {
        return fromSurah.fetchValue();
    }

    public void setFromSurah(Surah fromSurah)
    {
        this.fromSurah.insertValue(fromSurah);
    }

    public Surah getToSurah()
    {
        return toSurah.fetchValue();
    }

    public void setToSurah(Surah toSurah)
    {
        this.toSurah.insertValue(toSurah);
    }

    public Short getFromAya()
    {
        return Short.valueOf(fromAya.fetchValue());
    }

    public void setFromAya(Short fromAya)
    {
        this.fromAya.insertValue(fromAya);
    }

    public Short getToAya()
    {
        return Short.valueOf(toAya.fetchValue());
    }

    public void setToAya(Short toAya)
    {
        this.toAya.insertValue(toAya);
    }

    public void toAyaCallback(Consumer<Short> callback)
    {
        ((AnsarTextField) toAya.getControl()).setOnAction(e -> callback.accept(getToAya()));
    }

    public boolean isEmpty()
    {
        return ObjectChecker.areAllEmptyOrNull(getFromSurah(), getFromAya(), getToSurah(), getToAya());
    }

    public AbsRecitationInfo fetchRecitationInfo()
    {
        AbsRecitationInfo recitation = new RecitationInfo();
        recitation.setFromSurah(getFromSurah());
        recitation.setFromAya(getFromAya());
        recitation.setToSurah(getToSurah());
        recitation.setToAya(getToAya());
        return recitation;
    }

    public void updateSurahs(List<Surah> surahs)
    {
        configComboBox(fromSurah.getControl(), surahs);
        configComboBox(toSurah.getControl(), surahs);
    }

    private void configComboBox(AnsarComboBox<Surah> control, List<Surah> surahs)
    {
        control.config(surahs, Surah::nameByLang);
    }
}
