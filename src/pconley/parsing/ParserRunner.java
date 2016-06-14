package pconley.parsing;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import pconley.parsing.parse.Parser;

public class ParserRunner {

	public enum Status {
		VALID, MISSING, INVALID_FORMAT, INVALID_VALUE,
	};

	private Status status;

	private Map<String, Parser<?>> parsers = new HashMap<String, Parser<?>>();
	private List<String> failedParsers = new LinkedList<String>();

	public Parser<?> addParser(String name, Parser<?> parser) {
		parsers.put(name, parser);
		return parser;
	}

	public Status parse() {
		status = Status.VALID;

		for (String name : parsers.keySet()) {
			Parser<?> parser = parsers.get(name);
			if (!parser.isOptional() && !parser.isDefined()) {
				status = Status.MISSING;
				failedParsers.add(name);
			}
		}

		if (status != Status.VALID) {
			return status;
		}

		for (String name : parsers.keySet()) {
			Parser<?> parser = parsers.get(name);
			if (parser.isDefined() && !parser.parse()) {
				status = Status.INVALID_FORMAT;
				failedParsers.add(name);
			}
		}

		if (status != Status.VALID) {
			return status;
		}

		for (String name : parsers.keySet()) {
			Parser<?> parser = parsers.get(name);
			if (parser.isDefined() && !parser.validate()) {
				status = Status.INVALID_VALUE;
				failedParsers.add(name);
			}
		}

		return status;
	}

	public List<String> getFailedParsers() {
		return failedParsers;
	}

}
