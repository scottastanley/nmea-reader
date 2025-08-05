package com.bb.nmea.sentences.common;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LengthUnitTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}


	@Test
	public void testMeter() {
		String rawValue = "M";
		
		LengthUnit unit = LengthUnit.getLengthUnit(rawValue);
		
		Assert.assertEquals("Wrong unit", LengthUnit.METER, unit);
		Assert.assertEquals("Wrong raw value in unit", rawValue, unit.getRawValue());
	}


	@Test
	public void testUnsupported() {
		String rawValue = "HDGFBNS";
		
		try {
			LengthUnit.getLengthUnit(rawValue);
			Assert.fail("Should have thrown an exception");
		} catch (RuntimeException e) {
			// Ignore expected exception
		}
	}
}
