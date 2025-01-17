package entities.entries;

import utilities.*;

import javax.persistence.*;
import java.time.LocalDate;

@MappedSuperclass
public abstract class AnsarEntry implements IEntity
{
    private Long id;
    private LocalDate creationDate;

    public AnsarEntry()
    {
        creationDate = LocalDate.now();
    }

    @Id
    @GeneratedValue
    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public LocalDate getCreationDate()
    {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate)
    {
        this.creationDate = creationDate;
    }

    @Override
    public Result postCommit(Result result)
    {
        return result;
    }
}
