package entities;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Transient;

import utilities.Result;

@Entity
public class MemorizationTeacher extends Person {

	@Override
	@Transient
	public Result isValidForCommit() {
		Result result = super.isValidForCommit();
		LocalDate todayDate = LocalDate.now();
		 if (getBirthdate()!=null&&todayDate.getYear() - getBirthdate().getYear() < 12)
			result.accmulate(Result.createFailureResult("Teacher should have at least 12 years. Please check birthdate"));
		return result;
	}
	
}
