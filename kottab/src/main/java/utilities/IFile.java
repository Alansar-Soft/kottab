package utilities;

import javax.persistence.Transient;

public interface IFile {
	@Transient
	Result isValidForCommit();

	Result postCommit();
}
