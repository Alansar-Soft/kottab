package entities;

import utilities.Result;

import javax.persistence.*;

@MappedSuperclass
public abstract class StudentRelatedEntry extends AnsarEntry
{
    private Student student;
    private MemorizationGroup group;

    @ManyToOne
    public Student getStudent()
    {
        return student;
    }

    public void setStudent(Student student)
    {
        this.student = student;
    }

    @ManyToOne
    @JoinColumn(name = "group_id")
    public MemorizationGroup getGroup()
    {
        return group;
    }

    public void setGroup(MemorizationGroup group)
    {
        this.group = group;
    }

    @Override
    @Transient
    public Result isValidForCommit()
    {
        Result result = new Result();
        if (student == null)
            result.failure("You must enter student");
        return result;
    }

}
