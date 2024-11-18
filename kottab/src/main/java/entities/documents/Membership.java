package entities.documents;

import entities.files.Student;
import utilities.*;

import javax.persistence.*;

@Entity
public class Membership extends AbsReceiptVoucher
{
    private Student student;

    @ManyToOne
    @JoinColumn(name = "student_id")
    public Student getStudent()
    {
        return student;
    }

    public void setStudent(Student student)
    {
        this.student = student;
    }

    @Override
    public Result isValidForCommit(Result result)
    {
        super.isValidForCommit(result);
        ValidatorUtil.isEmptyRequired(getStudent(), "Student is required", result);
        return result;
    }
}
