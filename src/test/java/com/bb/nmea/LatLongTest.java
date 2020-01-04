package com.bb.nmea;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LatLongTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testCase1() {
        String raw = "3746.830";
        try {
            LatLong l = new LatLong(raw);
            
            Assert.assertEquals("Incorrect degrees", new Integer(37), l.getDegrees());
            Assert.assertEquals("Incorrect minutes", new Float(46.830), l.getMinutes());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testCase2() {
        String raw = "3747.554";
        try {
            LatLong l = new LatLong(raw);
            
            Assert.assertEquals("Incorrect degrees", new Integer(37), l.getDegrees());
            Assert.assertEquals("Incorrect minutes", new Float(47.554), l.getMinutes());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testCase3() {
        String raw = "12216.769";
        try {
            LatLong l = new LatLong(raw);
            
            Assert.assertEquals("Incorrect degrees", new Integer(122), l.getDegrees());
            Assert.assertEquals("Incorrect minutes", new Float(16.769), l.getMinutes());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testCase4() {
        String raw = "23416.057";
        try {
            LatLong l = new LatLong(raw);
            
            Assert.assertEquals("Incorrect degrees", new Integer(234), l.getDegrees());
            Assert.assertEquals("Incorrect minutes", new Float(16.057), l.getMinutes());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }
    
    public static void validateLatLong(final String msg, final Integer degrees, final Float minutes, final LatLong l) {
        Assert.assertEquals(msg + ": degrees", degrees, l.getDegrees());
        Assert.assertEquals(msg + ": minutes", minutes, l.getMinutes());
    }
}
