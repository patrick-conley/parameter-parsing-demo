package pconley.parsing;

public class NotNegativeIntegerParser extends IntegerParser {
	
	public NotNegativeIntegerParser(String param) {
		super(param);
	}
	
	@Override
	protected boolean isValueValid() {
		return value >= 0;
	}
	
}
