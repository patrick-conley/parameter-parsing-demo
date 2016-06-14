package pconley.parsing.parse;

import java.util.Date;

import pconley.parsing.util.Pair;
import pconley.parsing.validate.Validator;

public class DateRangeParser extends Parser<Pair<Date, Date>> {

	String strFrom;
	String strTo;

	public DateRangeParser(String strFrom, String strTo) {
		super("");
		this.strFrom = strFrom;
		this.strTo = strTo;

		addValidator(new Validator<Pair<Date, Date>>() {

			@Override
			public boolean validate(Pair<Date, Date> value) {
				return value.first().before(value.second());
			}
		});
	}

	@Override
	public boolean isDefined() {
		return !(strFrom == null || strTo == null || strFrom.isEmpty() || strTo.isEmpty());
	}

	@Override
	public boolean parse() {
		Date dateFrom, dateTo;

		try {
			dateFrom = new Date(Long.parseLong(strFrom));
			dateTo = new Date(Long.parseLong(strTo));
		} catch (NumberFormatException e) {
			return false;
		}

		value = new Pair<Date, Date>(dateFrom, dateTo);
		return true;
	}

}
