package pconley.parsing;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class ParserFactoryIntegrationTest {

	private Map<String, String> params;

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
		ParserFactory factory = new ParserFactory(params);
		factory.addNotNegativeIntegerParser("userId");
		factory.addNotNegativeIntegerParser("sensorId");
		factory.addParser(
				"dateRange",
				new DateRangeParser(params.get("dateFrom"), params
						.get("dateTo")));
		factory.addBooleanParser("clean");
		factory.addNotNegativeIntegerParser("resampleType");

		assertEquals(ParserFactory.Status.VALID, factory.parse());
		assertEquals(0, factory.getFailedParsers().size());
	}

	@Test
	public void oneInvalidValue() {
		params.put("userId", "-1");

		ParserFactory factory = new ParserFactory(params);
		factory.addNotNegativeIntegerParser("userId");
		factory.addNotNegativeIntegerParser("sensorId");
		factory.addParser(
				"dateRange",
				new DateRangeParser(params.get("dateFrom"), params
						.get("dateTo")));
		factory.addBooleanParser("clean");
		factory.addNotNegativeIntegerParser("resampleType");

		assertEquals(ParserFactory.Status.INVALID_VALUE, factory.parse());
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

		ParserFactory factory = new ParserFactory(params);
		factory.addNotNegativeIntegerParser("userId");
		factory.addNotNegativeIntegerParser("sensorId");
		factory.addParser(
				"dateRange",
				new DateRangeParser(params.get("dateFrom"), params
						.get("dateTo")));
		factory.addBooleanParser("clean");
		factory.addNotNegativeIntegerParser("resampleType");

		assertEquals(ParserFactory.Status.INVALID_VALUE, factory.parse());
		assertEquals(expectedInvalidParams, new HashSet<String>(factory.getFailedParsers()));
	}
	
	@Test
	public void invalidValueAndMissingValue() {
		params.put("userId", "-1");
		params.remove("sensorId");

		ParserFactory factory = new ParserFactory(params);
		factory.addNotNegativeIntegerParser("userId");
		factory.addNotNegativeIntegerParser("sensorId");
		factory.addParser(
				"dateRange",
				new DateRangeParser(params.get("dateFrom"), params
						.get("dateTo")));
		factory.addBooleanParser("clean");
		factory.addNotNegativeIntegerParser("resampleType");

		assertEquals(ParserFactory.Status.MISSING, factory.parse());
		assertEquals(1, factory.getFailedParsers().size());
		assertEquals("sensorId", factory.getFailedParsers().get(0));
	}
}
