package com.bb.nmea.sentences.common;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SentenceDateTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCase_pre2000() {
		String strValue = "030593";
		
		try {
			SentenceDate d = new SentenceDate(strValue);
			
			Assert.assertEquals("Incorrect day", Integer.valueOf(03), d.getDayOfMonth());
			Assert.assertEquals("Incorrect month", Integer.valueOf(05), d.getMonth());
			Assert.assertEquals("Incorrect year", Integer.valueOf(1993), d.getYear());
		} catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
		}
	}

	@Test
	public void testCase_1970() {
		String strValue = "011570";
		
		try {
			SentenceDate d = new SentenceDate(strValue);
			
			Assert.assertEquals("Incorrect day", Integer.valueOf(01), d.getDayOfMonth());
			Assert.assertEquals("Incorrect month", Integer.valueOf(15), d.getMonth());
			Assert.assertEquals("Incorrect year", Integer.valueOf(1970), d.getYear());
		} catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
		}
	}

	@Test
	public void testCase_2069() {
		String strValue = "011569";
		
		try {
			SentenceDate d = new SentenceDate(strValue);
			
			Assert.assertEquals("Incorrect day", Integer.valueOf(01), d.getDayOfMonth());
			Assert.assertEquals("Incorrect month", Integer.valueOf(15), d.getMonth());
			Assert.assertEquals("Incorrect year", Integer.valueOf(2069), d.getYear());
		} catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
		}
	}
	
	@Test
	public void testCase_2000() {
		String strValue = "180900";
		
		try {
			SentenceDate d = new SentenceDate(strValue);
			
			Assert.assertEquals("Incorrect day", Integer.valueOf(18), d.getDayOfMonth());
			Assert.assertEquals("Incorrect month", Integer.valueOf(9), d.getMonth());
			Assert.assertEquals("Incorrect year", Integer.valueOf(2000), d.getYear());
		} catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
		}
	}
	
	@Test
	public void testCase_post2000() {
		String strValue = "281205";
		
		try {
			SentenceDate d = new SentenceDate(strValue);
			
			Assert.assertEquals("Incorrect day", Integer.valueOf(28), d.getDayOfMonth());
			Assert.assertEquals("Incorrect month", Integer.valueOf(12), d.getMonth());
			Assert.assertEquals("Incorrect year", Integer.valueOf(2005), d.getYear());
		} catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
		}
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@Test
	public void testEquals_SpecialCases() {
		String strValue = "281205";
		
		try {
			SentenceDate d = new SentenceDate(strValue);
			
			Assert.assertFalse("NULL other should be false", d.equals(null));
			Assert.assertFalse("Not SentenceDate other should be false", d.equals(new String("")));
		} catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
		}
	}
	
	@Test
	public void testEquals() {
		try {
			SentenceDate d1 = new SentenceDate("281205");			
			Assert.assertTrue("Should be equal, d1==d1", d1.equals(d1));

			SentenceDate d2 = new SentenceDate("281205");
			Assert.assertTrue("Should be equal, d1==d2", d1.equals(d2));
			Assert.assertTrue("Should be equal,d2==d1", d2.equals(d1));
			
			SentenceDate d3 = new SentenceDate("151205");
			Assert.assertFalse("Day missmatch should be false, d1!=d3", d1.equals(d3));
			Assert.assertFalse("Day missmatch should be false, d3!=d1", d3.equals(d1));
			
			SentenceDate d4 = new SentenceDate("280905");
			Assert.assertFalse("Month missmatch should be false, d1!=d4", d1.equals(d4));
			Assert.assertFalse("Month missmatch should be false, d4!=d1", d4.equals(d1));

			SentenceDate d5 = new SentenceDate("281222");
			Assert.assertFalse("Year missmatch should be false, d1!=d5", d1.equals(d5));
			Assert.assertFalse("Year missmatch should be false, d5!=d1", d5.equals(d1));

		} catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
		}
	}
	
	@Test
	public void testHashCode() {
		try {
			SentenceDate d1 = new SentenceDate("281205");
			SentenceDate d2 = new SentenceDate("281205");
			Assert.assertEquals("d1 and d2 should have same hashcode", d1.hashCode(), d2.hashCode());
			
			SentenceDate d3 = new SentenceDate("151205");
			Assert.assertNotEquals("Hashcode should differ for day missmatch", d1.hashCode(), d3.hashCode());
			
			SentenceDate d4 = new SentenceDate("280905");
			Assert.assertNotEquals("Hashcode should differ for month missmatch", d1.hashCode(), d4.hashCode());

			SentenceDate d5 = new SentenceDate("281222");
			Assert.assertNotEquals("Hashcode should differ for year missmatch", d1.hashCode(), d5.hashCode());

		} catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
		}
	}
}
