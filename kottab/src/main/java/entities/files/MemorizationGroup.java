package entities.files;

import utilities.Result;

import javax.persistence.*;
import java.util.List;

@Entity
public class MemorizationGroup extends AnsarFile
{
    private MemorizationTeacher teacher;
    private MemorizationTeacher assistantTeacher;
    private List<Student> students;
    private GroupLevel groupLevel;

    @ManyToOne
    public MemorizationTeacher getTeacher()
    {
        return teacher;
    }

    public void setTeacher(MemorizationTeacher teacher)
    {
        this.teacher = teacher;
    }

    @OneToOne
    public MemorizationTeacher getAssistantTeacher()
    {
        return assistantTeacher;
    }

    public void setAssistantTeacher(MemorizationTeacher assistantTeacher)
    {
        this.assistantTeacher = assistantTeacher;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "group")
    public List<Student> getStudents()
    {
        return students;
    }

    public void setStudents(List<Student> students)
    {
        this.students = students;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "groupLevel_id")
    public GroupLevel getGroupLevel()
    {
        return groupLevel;
    }

    public void setGroupLevel(GroupLevel groupLevel)
    {
        this.groupLevel = groupLevel;
    }

    @Override
    @Transient
    public Result isValidForCommit(Result result)
    {
        super.isValidForCommit(result);
        if (getGroupLevel() == null)
            result.failure("You must choose group level");
        if (getTeacher() == null)
            result.failure("You must choose teacher");
        return result;
    }

}
