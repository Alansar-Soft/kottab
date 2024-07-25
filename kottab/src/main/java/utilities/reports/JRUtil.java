package utilities.reports;

import net.sf.jasperreports.functions.annotations.Function;
import net.sf.jasperreports.functions.annotations.FunctionCategories;
import net.sf.jasperreports.functions.annotations.FunctionParameter;
import net.sf.jasperreports.functions.annotations.FunctionParameters;
import utilities.*;
@FunctionCategories({ utilities.reports.Util.class })
public class JRUtil {

	@Function("SURAH_NAME")
	@FunctionParameters({
		@FunctionParameter("surahNo")
	})
	public static String SURAH_NAME(Short surahNo)
	{
		return	SurahsUtil.fetchSurahName(surahNo.byteValue());
	}

}
 