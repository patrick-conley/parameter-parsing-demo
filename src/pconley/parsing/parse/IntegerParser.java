package pconley.parsing.parse;


public class IntegerParser extends Parser<Integer> {

	public IntegerParser(String param) {
		super(param);
	}

	@Override
	public boolean parse() {
		try {
			value = Integer.parseInt(param);
		} catch (NumberFormatException e) {
			return false;
		}

		return true;
	}

}
