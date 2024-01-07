package utilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import entities.AnsarBaseEntity;

public class ObjectChecker {
	@SuppressWarnings({ "rawtypes" })
	public static boolean isNotEmptyOrZeroOrNull(Object object) {
		return !isEmptyOrZeroOrNull(object);
	}

	@SuppressWarnings({ "rawtypes" })
	public static boolean isEmptyOrZeroOrNull(Object object) {
		if (object == null)
			return true;
		if (object instanceof String)
			return ((String) object).isBlank();
		if (object instanceof Collection)
			return ((Collection) object).isEmpty();
		if (object instanceof Map)
			return ((Map) object).isEmpty();
		if (object instanceof Number && Double.valueOf(0.0).equals(((Number) object).doubleValue()))
			return true;
		return false;
	}

	public static boolean areEqual(Object object1, Object object2) {
		if (object1 == object2)
			return true;
		if (object1.equals(object2))
			return true;
		if (isAnyEmptyOrNull(object1, object2))
			return false;
		if (object1 instanceof AnsarBaseEntity && object2 instanceof AnsarBaseEntity)
			return areEqual(((AnsarBaseEntity) object1).getCode(), ((AnsarBaseEntity) object2).getCode());
		return false;
	}

	public static boolean areNotEqual(Object object1, Object object2) {
		return !areEqual(object1, object2);
	}

	private static boolean isAnyEmptyOrNull(Object... objs) {
		for (int i = 0; i < objs.length; i++) {
			if (isEmptyOrZeroOrNull(objs[i]))
				return true;
		}
		return false;
	}

	public static String toString(Object value) {
		if (value == null)
			return "";
		String val = ResourceUtility.translate(value.toString());
		if (ObjectChecker.isNotEmptyOrZeroOrNull(val))
			return val;
		return value.toString();
	}

	public static Boolean toFalseIfNull(Boolean value) {
		if (isEmptyOrZeroOrNull(value))
			return false;
		return value;
	}

	public static <T> List<String> translateList(T... values) {
		List<String> list = new ArrayList<>();
		for (int i = 0; i < values.length; i++) {
			list.add(ResourceUtility.translate(values[i].toString()));
		}
		return list;
	}

	public static int toZeroIfNull(Byte numberOfSurah) {
		return numberOfSurah == null ? 0 : numberOfSurah;
	}

	public static boolean areAllEmptyOrNull(Object... objects) {
		for (Object obj : objects) {
			if (isNotEmptyOrZeroOrNull(obj))
				return false;
		}
		return true;
	}
}
