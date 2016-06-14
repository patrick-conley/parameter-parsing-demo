package pconley.parsing.validate;

public interface Validator<T> {

	public boolean validate(T value);
}
