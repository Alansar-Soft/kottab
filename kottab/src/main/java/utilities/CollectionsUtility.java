package utilities;

import java.util.List;
import java.util.function.Predicate;

public class CollectionsUtility {
	public static <T> T fetchFirstMatched(List<T> list, Predicate<T> predicate) {
		if (ObjectChecker.isEmptyOrNull(list))
			return null;
		return list.stream().filter(predicate).findFirst().orElse(null);
	}
}
