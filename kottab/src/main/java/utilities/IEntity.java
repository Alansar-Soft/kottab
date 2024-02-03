package utilities;

import javax.persistence.Transient;

public interface IEntity {
	@Transient
	Result isValidForCommit();

	Result postCommit();
}
