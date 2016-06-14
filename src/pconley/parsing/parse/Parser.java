package pconley.parsing.parse;

import java.util.LinkedList;
import java.util.List;

import pconley.parsing.validate.Validator;

public abstract class Parser<T> {

	protected String param;
	boolean optional = false;
	List<Validator<T>> validators = new LinkedList<Validator<T>>();

	protected T value = null;

	public Parser(String param) {
		this.param = param;
	}

	public Parser(String param, boolean optional) {
		this.param = param;
		this.optional = optional;
	}

	public final void addValidator(Validator<T> validator) {
		validators.add(validator);
	}

	public boolean isOptional() {
		return optional;
	}

	public final T get() {
		return value;
	}

	public boolean isDefined() {
		return !(param == null || param.isEmpty());
	}

	public boolean parse() {
		return true;
	}

	public boolean validate() {
		for (Validator<T> v : validators) {
			if (!v.validate(value)) {
				return false;
			}
		}

		return true;
	}
}
