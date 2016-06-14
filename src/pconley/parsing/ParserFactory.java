package pconley.parsing;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ParserFactory {

	public enum Status {
		VALID, MISSING, INVALID_FORMAT, INVALID_VALUE,
	};

	private Map<String, String> params;
	private Map<String, Parser<?>> parsers;

	private Status status;
	private List<String> failedParsers;

	public ParserFactory(Map<String, String> params) {
		this.params = params;

		parsers = new HashMap<String, Parser<?>>();
		failedParsers = new LinkedList<String>();
	}

	public Status parse() {
		status = Status.VALID;
		
		for (String name : parsers.keySet()) {
			if (!parsers.get(name).isPresent()) {
				status = Status.MISSING;
				failedParsers.add(name);
			}
		}

		if (status != Status.VALID) {
			return status;
		}

		for (String name : parsers.keySet()) {
			if (!parsers.get(name).isFormatValid()) {
				status = Status.INVALID_FORMAT;
				failedParsers.add(name);
			}
		}

		if (status != Status.VALID) {
			return status;
		}

		for (String name : parsers.keySet()) {
			if (!parsers.get(name).isValueValid()) {
				status = Status.INVALID_VALUE;
				failedParsers.add(name);
			}
		}
		
		return status;
	}
	
	public List<String> getFailedParsers() {
		return failedParsers;
	}
	
	public Parser<?> addParser(String name, Parser<?> parser) {
		parsers.put(name, parser);
		return parser;
	}
	
	@SuppressWarnings("unchecked")
	public Parser<Integer> addIntegerParser(String name) {
		return (Parser<Integer>) addParser(name, new IntegerParser(params.get(name)));
	}
	
	@SuppressWarnings("unchecked")
	public Parser<Integer> addNotNegativeIntegerParser(String name) {
		return (Parser<Integer>) addParser(name, new NotNegativeIntegerParser(params.get(name)));
	}

	@SuppressWarnings("unchecked")
	public Parser<Boolean> addBooleanParser(String name) {
		return (Parser<Boolean>) addParser(name, new BooleanParser(params.get(name)));
	}

}
