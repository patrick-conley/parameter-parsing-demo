package pconley.parsing;

public class IntegerParser extends Parser<Integer> {

	public IntegerParser(String param) {
		super(param);
	}
	
	@Override
	protected boolean isFormatValid() {
		try {
			value = Integer.parseInt(param);
		} catch (NumberFormatException e) {
			return false;
		}
		
		return true;
	}
	
}
