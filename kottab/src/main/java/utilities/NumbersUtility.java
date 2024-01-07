package utilities;

public class NumbersUtility {
	public static boolean isZeroOrNull(Number num) {
		return ObjectChecker.areEqual(num.doubleValue(), 0d);
	}

	public static boolean isNotZeroOrNull(Number num) {
		return !isZeroOrNull(num);
	}

	public static Short castToShort(Number num) {
		return num.shortValue();
	}

	public static Byte castToByte(Number num) {
		return num.byteValue();
	}
	}
