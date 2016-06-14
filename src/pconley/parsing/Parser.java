package pconley.parsing;

public abstract class Parser<T> {

	protected String param;
	protected T value = null;

	// FIXME: add an "optional" flag
	public Parser(String param) {
		this.param = param;
	}

	protected boolean isPresent() {
		return !(param == null || param.isEmpty());
	}

	// FIXME: name doesn't make clear that this sets the `value` as well
	protected boolean isFormatValid() {
		return true;
	}

	// FIXME: don't include this method - use addValidator(Validator v) so
	// implementations of Validator can be added at runtime
	protected boolean isValueValid() {
		return true;
	}

	public final T get() {
		return value;
	}
}
