package entities.files;

import entities.AnsarBaseEntity;
import org.hibernate.annotations.Nationalized;
import utilities.*;

import javax.persistence.*;

@MappedSuperclass
public abstract class AnsarFile extends AnsarBaseEntity
{
    private String name;
    private Boolean preventUsage;

    @Nationalized
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Boolean getPreventUsage()
    {
        return preventUsage;
    }

    public void setPreventUsage(Boolean preventUsage)
    {
        this.preventUsage = preventUsage;
    }

    @Override
    @Transient
    public Result isValidForCommit(Result result)
    {
        if (ObjectChecker.isEmptyOrZeroOrNull(name))
            result.failure("You must enter name");
        return result;
    }

}
