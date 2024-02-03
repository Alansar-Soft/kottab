package entities;

import javax.persistence.Entity;

import utilities.Result;

@Entity
public class AbsenceEntry extends StudentRelatedEntry {

	@Override
	public Result postCommit() {
		return new Result();
	}

}
