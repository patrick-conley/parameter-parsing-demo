package pconley.parsing;

public class BooleanParser extends Parser<Boolean> {

	public BooleanParser(String param) {
		super(param);
	}

	@Override
	protected boolean isFormatValid() {
		try {
			value = Boolean.parseBoolean(param);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
}
