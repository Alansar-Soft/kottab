package entities.entries;

import javax.persistence.Entity;

import utilities.Result;

@Entity
public class AbsenceEntry extends StudentRelatedEntry
{

	@Override
	public Result postCommit(Result result) {
		return result;
	}

}
