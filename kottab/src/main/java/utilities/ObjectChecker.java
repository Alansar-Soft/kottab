package utilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import entities.AnsarBaseEntity;
import application.Country;

public class ObjectChecker {
	@SuppressWarnings({ "rawtypes" })
	public static boolean isNotEmptyOrNull(Object object) {
		return !isEmptyOrNull(object);
	}

	@SuppressWarnings({ "rawtypes" })
	public static boolean isEmptyOrNull(Object object) {
		if (object == null)
			return true;
		if (object instanceof String)
			return ((String) object).isBlank();
		if (object instanceof Collection)
			return ((Collection) object).isEmpty();
		if (object instanceof Map)
			return ((Map) object).isEmpty();
		return false;
	}

	public static boolean areEqual(Object object1, Object object2) {
		if (object1 == object2)
			return true;
		if (isAnyEmptyOrNull(object1, object2))
			return false;
		if (object1 instanceof AnsarBaseEntity && object2 instanceof AnsarBaseEntity)
			return areEqual((AnsarBaseEntity) object1, (AnsarBaseEntity) object2);
		if (object1.equals(object2))
			return true;
		return false;
	}

	private static boolean areEqual(AnsarBaseEntity object1, AnsarBaseEntity object2) {
		return object1.getId().equals(object2.getId());
	}

	public static boolean areNotEqual(Object object1, Object object2) {
		return !areEqual(object1, object2);
	}

	private static boolean isAnyEmptyOrNull(Object... objs) {
		for (int i = 0; i < objs.length; i++) {
			if (isEmptyOrNull(objs[i]))
				return true;
		}
		return false;
	}

	public static String toString(Object value) {
		if (value == null)
			return "";
		String val = ResourceUtility.id(value.toString());
		if (ObjectChecker.isNotEmptyOrNull(val))
			return val;
		return value.toString();
	}

	public static Boolean toFalseIfNull(Boolean value) {
		if (isEmptyOrNull(value))
			return false;
		return value;
	}

	public static List<String> translateList(Object... values) {
		List<String> list = new ArrayList();
		for (int i = 0; i < values.length; i++) {
			list.add(ResourceUtility.id(values[i].toString()));
		}
		return list;
	}

	public static int toZeroIfNull(Byte numberOfSurah) {
		return numberOfSurah == null ? 0 : numberOfSurah;
	}

}
