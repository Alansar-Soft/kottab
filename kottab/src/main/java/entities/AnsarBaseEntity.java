package entities;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import org.hibernate.annotations.Nationalized;
import utilities.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@JsonIncludeProperties(value = {"id", "code", "name"})
public abstract class AnsarBaseEntity implements IEntity
{
    private Long id;
    private String code;
    private LocalDateTime creationDate;

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

    @Column(unique = true)
    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }



    public LocalDateTime getCreationDate()
    {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate)
    {
        this.creationDate = creationDate;
    }

    @Override
    public Result postCommit(Result result)
    {
        return result;
    }
}
