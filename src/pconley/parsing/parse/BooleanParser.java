package pconley.parsing.parse;


public class BooleanParser extends Parser<Boolean> {

	public BooleanParser(String param) {
		super(param);
	}

	public BooleanParser(String param, boolean optional) {
		super(param, optional);
	}

	@Override
	public boolean parse() {
		try {
			value = Boolean.parseBoolean(param);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
}
