package entities.documents;

import entities.AnsarBaseEntity;
import utilities.Result;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AnsarDocument extends AnsarBaseEntity
{
    @Override
    public Result isValidForCommit(Result result)
    {
        return result;
    }
}
