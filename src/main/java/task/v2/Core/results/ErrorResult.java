package task.v2.Core.results;

public class ErrorResult extends Result{
	public ErrorResult() {
		super(false);
	}
	
	public ErrorResult(String message) {
		super(false,message);
	}
}
