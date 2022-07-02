package com.bb.nmea.sentences.common;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SpeedUnitsTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testKnots() {
        String rawValue = "N";
        
        try {
            SpeedUnits e = SpeedUnits.getSpeedUnits(rawValue);
            Assert.assertEquals("Wrong units", SpeedUnits.KNOTS, e);
            Assert.assertEquals("Incorrect raw units", rawValue, e.getRawValue());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testKmPerHr() {
        String rawValue = "K";
        
        try {
            SpeedUnits e = SpeedUnits.getSpeedUnits(rawValue);
            Assert.assertEquals("Wrong units", SpeedUnits.KM_PER_HR, e);
            Assert.assertEquals("Incorrect raw units", rawValue, e.getRawValue());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }
}
