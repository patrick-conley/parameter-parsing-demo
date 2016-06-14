package pconley.parsing.parse;

import pconley.parsing.validate.Validator;

public class NotNegativeIntegerParser extends IntegerParser {

	public NotNegativeIntegerParser(String param) {
		super(param);

		addValidator(new Validator<Integer>() {

			@Override
			public boolean validate(Integer value) {
				return value >= 0;
			}
		});
	}
}
