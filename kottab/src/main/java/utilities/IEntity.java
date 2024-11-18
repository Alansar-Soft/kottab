package utilities;

import javax.persistence.Transient;

public interface IEntity
{
    @Transient
    Result isValidForCommit(Result result);

    Result postCommit(Result result);
}
