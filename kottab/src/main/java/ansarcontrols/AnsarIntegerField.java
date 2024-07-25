package ansarcontrols;

import utilities.ObjectChecker;

public class AnsarIntegerField extends AnsarTextField<Integer>
{
    @Override
    public Integer fetchValue()
    {
        return Integer.valueOf(ObjectChecker.toStringOrEmpty(super.fetchValue()));
    }
}
