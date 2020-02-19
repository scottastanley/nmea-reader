package com.bb.nmea.sentences.common;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

public class TemperatureUnitsTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testWest() {
        String rawStr = "C";
        
        try {
            TemperatureUnits e = TemperatureUnits.getTemperatureUnits(rawStr);
            Assert.assertEquals("Wrong units", TemperatureUnits.CELCIUS, e);
            Assert.assertEquals("Incorrect raw units", rawStr, e.getRawValue());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testUnsupported() {
        String rawStr = "KJDHFLKJSDF";
        
        try {
            try {
                TemperatureUnits.getTemperatureUnits(rawStr);
                Assert.fail("Should throw a RuntimeException");
            } catch (RuntimeException e) {
                // Ignore expected exception
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }
}
