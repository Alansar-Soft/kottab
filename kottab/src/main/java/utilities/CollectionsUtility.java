package utilities;

import java.util.List;
import java.util.function.Predicate;

public class CollectionsUtility {
	public static <T> T fetchFirstMatched(List<T> list, Predicate<T> predicate) {
		if (ObjectChecker.isEmptyOrZeroOrNull(list))
			return null;
		return list.stream().filter(predicate).findFirst().orElse(null);
	}

	public static <T> T fetchFirstElement(List<T> resultList) {
		if (ObjectChecker.isEmptyOrZeroOrNull(resultList))
			return null;
		return resultList.get(0);
	}
}
