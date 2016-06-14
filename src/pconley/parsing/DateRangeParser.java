package pconley.parsing;

import java.util.Date;

import pconley.parsing.util.Pair;

public class DateRangeParser extends Parser<Pair<Date,Date>> {
	
	String strFrom;
	String strTo;

	public DateRangeParser(String strFrom, String strTo) {
		super("");
		this.strFrom = strFrom;
		this.strTo = strTo;
	}

	@Override
	protected boolean isPresent() {
		return !(strFrom == null || strTo == null || strFrom.isEmpty() || strTo.isEmpty());
	}
	
	@Override
	protected boolean isFormatValid() {
		Date dateFrom, dateTo;

		try {
			dateFrom = new Date(Long.parseLong(strFrom));
			dateTo = new Date(Long.parseLong(strTo));
		} catch (NumberFormatException e) {
			return false;
		}
		
		value = new Pair<Date,Date>(dateFrom, dateTo);
		return true;
	}
	
	@Override
	protected boolean isValueValid() {
		return value.first().before(value.second());
	}

}
