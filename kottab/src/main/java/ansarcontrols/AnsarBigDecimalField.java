package ansarcontrols;

import utilities.ObjectChecker;

import java.math.BigDecimal;

public class AnsarBigDecimalField extends AnsarTextField<BigDecimal>
{
    @Override
    public BigDecimal fetchValue()
    {
        if (ObjectChecker.isEmptyOrZeroOrNull(getText()))
            return BigDecimal.ZERO;
        return new BigDecimal(getText());
    }
}
