package utilities;

public class ValidatorUtil
{
    public static void isEmptyRequired(Object field, String message, Result result)
    {
        if (ObjectChecker.isEmptyOrZeroOrNull(field))
            result.failure(message);
    }
}
