package utilities;

public class Result {
	private boolean failed;
	private String message = "";

	public boolean isFailed() {
		return failed;
	}

	public void setFailed(boolean failed) {
		this.failed = failed;
	}

	public String getMessage() {
		return message;
	}

	private void setMessage(String massage) {
		this.message = massage;
	}

	public static Result createFailureResult(String message) {
		Result result = new Result();
		result.setFailed(true);
		result.setMessage(ResourceUtility.translateMessage(message));
		return result;
	}

	public void failure(String message) {
		setFailed(true);
		String newMessage = ResourceUtility.translateMessage(message);
		setMessage(ObjectChecker.isNotEmptyOrZeroOrNull(getMessage()) ? getMessage() + "\n" + newMessage : newMessage);
	}

	public void accmulate(Result result) {
		if (failed == false)
			failed = result.isFailed();
		String resultMessage = result.getMessage();
		if (ObjectChecker.isEmptyOrZeroOrNull(resultMessage))
			return;
		message = ObjectChecker.isEmptyOrZeroOrNull(message) ? resultMessage
				: message + "\n" + ResourceUtility.translateMessage(resultMessage);
	}
}
