package entities.files;

import utilities.Result;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class MemorizationTeacher extends Person
{

    @Override
    @Transient
    public Result isValidForCommit(Result result)
    {
        super.isValidForCommit(result);
        LocalDate todayDate = LocalDate.now();
        if (getBirthdate() != null && todayDate.getYear() - getBirthdate().getYear() < 12)
            result.failure("Teacher should have at least 12 years. Please check birthdate");
        return result;
    }

}
