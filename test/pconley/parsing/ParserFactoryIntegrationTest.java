package pconley.parsing;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import pconley.parsing.parse.BooleanParser;
import pconley.parsing.parse.DateRangeParser;
import pconley.parsing.parse.NotNegativeIntegerParser;
import pconley.parsing.parse.Parser;
import pconley.parsing.validate.Validator;

public class ParserFactoryIntegrationTest {

	private Map<String, String> params;

	private class ResampleTypeValidator implements Validator<Integer> {

		@Override
		public boolean validate(Integer value) {
			return value == 1 || value == 3 || value == 5;
		}
	}

	@Before
	public void setDefaultParams() {
		params = new HashMap<String, String>();
		params.put("userId", "42409");
		params.put("sensorId", "11211");
		params.put("dateFrom", "1465776000000");
		params.put("dateTo", "1465862400000");
		params.put("clean", "1");
		params.put("resampleType", "3");
	}

	@Test
	public void allValid() {
		ParserRunner factory = new ParserRunner();
		factory.addParser("userId", new NotNegativeIntegerParser(params.get("userId")));
		factory.addParser("sensorId", new NotNegativeIntegerParser(params.get("sensorId")));
		factory.addParser( "dateRange", new DateRangeParser(params.get("dateFrom"), params.get("dateTo")));
		factory.addParser("clean", new BooleanParser(params.get("clean")));;

		Parser<Integer> resampleTypeParser = new NotNegativeIntegerParser(params.get("resampleType"));
		resampleTypeParser.addValidator(new ResampleTypeValidator());
		factory.addParser("resampleType", resampleTypeParser);

		assertEquals(ParserRunner.Status.VALID, factory.parse());
		assertEquals(0, factory.getFailedParsers().size());
	}

	@Test
	public void oneInvalidValue() {
		params.put("userId", "-1");

		ParserRunner factory = new ParserRunner();
		factory.addParser("userId", new NotNegativeIntegerParser(params.get("userId")));
		factory.addParser("sensorId", new NotNegativeIntegerParser(params.get("sensorId")));
		factory.addParser( "dateRange", new DateRangeParser(params.get("dateFrom"), params.get("dateTo")));
		factory.addParser("clean", new BooleanParser(params.get("clean")));;
		factory.addParser("resampleType", new NotNegativeIntegerParser(params.get("resampleType")));

		assertEquals(ParserRunner.Status.INVALID_VALUE, factory.parse());
		assertEquals(1, factory.getFailedParsers().size());
		assertEquals("userId", factory.getFailedParsers().get(0));
	}

	@Test
	public void twoInvalidValues() {
		params.put("userId", "-1");
		params.put("dateFrom", "100");
		params.put("dateTo", "50");

		Set<String> expectedInvalidParams = new HashSet<String>();
		expectedInvalidParams.add("userId");
		expectedInvalidParams.add("dateRange");

		ParserRunner factory = new ParserRunner();
		factory.addParser("userId", new NotNegativeIntegerParser(params.get("userId")));
		factory.addParser("sensorId", new NotNegativeIntegerParser(params.get("sensorId")));
		factory.addParser( "dateRange", new DateRangeParser(params.get("dateFrom"), params.get("dateTo")));
		factory.addParser("clean", new BooleanParser(params.get("clean")));;
		factory.addParser("resampleType", new NotNegativeIntegerParser(params.get("resampleType")));

		assertEquals(ParserRunner.Status.INVALID_VALUE, factory.parse());
		assertEquals(expectedInvalidParams, new HashSet<String>(factory.getFailedParsers()));
	}

	@Test
	public void invalidValueAndMissingValue() {
		params.put("userId", "-1");
		params.remove("sensorId");

		ParserRunner factory = new ParserRunner();
		factory.addParser("userId", new NotNegativeIntegerParser(params.get("userId")));
		factory.addParser("sensorId", new NotNegativeIntegerParser(params.get("sensorId")));
		factory.addParser( "dateRange", new DateRangeParser(params.get("dateFrom"), params.get("dateTo")));
		factory.addParser("clean", new BooleanParser(params.get("clean")));;
		factory.addParser("resampleType", new NotNegativeIntegerParser(params.get("resampleType")));

		assertEquals(ParserRunner.Status.MISSING, factory.parse());
		assertEquals(1, factory.getFailedParsers().size());
		assertEquals("sensorId", factory.getFailedParsers().get(0));
	}

	@Test
	public void missingOptionalValue() {
		params.remove("clean");

		ParserRunner factory = new ParserRunner();
		factory.addParser("userId", new NotNegativeIntegerParser(params.get("userId")));
		factory.addParser("sensorId", new NotNegativeIntegerParser(params.get("sensorId")));
		factory.addParser( "dateRange", new DateRangeParser(params.get("dateFrom"), params.get("dateTo")));
		factory.addParser("clean", new BooleanParser(params.get("clean"), true));;
		factory.addParser("resampleType", new NotNegativeIntegerParser(params.get("resampleType")));

		assertEquals(ParserRunner.Status.VALID, factory.parse());
		assertEquals(0, factory.getFailedParsers().size());
	}
}
