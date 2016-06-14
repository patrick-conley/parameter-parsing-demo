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
	
	protected boolean isFormatValid() {
		return true;
	}
	
	protected boolean isValueValid() {
		return true;
	}
	
	public final T get() {
		return value;
	}
}
