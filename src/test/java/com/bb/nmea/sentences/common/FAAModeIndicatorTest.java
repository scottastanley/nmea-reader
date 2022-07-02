package com.bb.nmea.sentences.common;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FAAModeIndicatorTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testAutonomous() {
        String rawStr = "A";
        
        try {
            FAAModeIndicator e = FAAModeIndicator.getFAAModeIndicator(rawStr);
            Assert.assertEquals("Wrong FAA mode", FAAModeIndicator.AUTONOMOUS_MODE, e);
            Assert.assertEquals("Incorrect raw mode", rawStr, e.getRawValue());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testDifferential() {
        String rawStr = "D";
        
        try {
            FAAModeIndicator e = FAAModeIndicator.getFAAModeIndicator(rawStr);
            Assert.assertEquals("Wrong FAA mode", FAAModeIndicator.DIFFERENTIAL_MODE, e);
            Assert.assertEquals("Incorrect raw mode", rawStr, e.getRawValue());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testEstimated() {
        String rawStr = "E";
        
        try {
            FAAModeIndicator e = FAAModeIndicator.getFAAModeIndicator(rawStr);
            Assert.assertEquals("Wrong FAA mode", FAAModeIndicator.ESTIMATED, e);
            Assert.assertEquals("Incorrect raw mode", rawStr, e.getRawValue());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testRTK_Float() {
        String rawStr = "F";
        
        try {
            FAAModeIndicator e = FAAModeIndicator.getFAAModeIndicator(rawStr);
            Assert.assertEquals("Wrong FAA mode", FAAModeIndicator.RTK_FLOAT_MODE, e);
            Assert.assertEquals("Incorrect raw mode", rawStr, e.getRawValue());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testManual() {
        String rawStr = "M";
        
        try {
            FAAModeIndicator e = FAAModeIndicator.getFAAModeIndicator(rawStr);
            Assert.assertEquals("Wrong FAA mode", FAAModeIndicator.MANUAL_INPUT_MODE, e);
            Assert.assertEquals("Incorrect raw mode", rawStr, e.getRawValue());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testDataNotValid() {
        String rawStr = "N";
        
        try {
            FAAModeIndicator e = FAAModeIndicator.getFAAModeIndicator(rawStr);
            Assert.assertEquals("Wrong FAA mode", FAAModeIndicator.DATA_NOT_VALID, e);
            Assert.assertEquals("Incorrect raw mode", rawStr, e.getRawValue());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testPrecise() {
        String rawStr = "P";
        
        try {
            FAAModeIndicator e = FAAModeIndicator.getFAAModeIndicator(rawStr);
            Assert.assertEquals("Wrong FAA mode", FAAModeIndicator.PRECISE, e);
            Assert.assertEquals("Incorrect raw mode", rawStr, e.getRawValue());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testRTK_Integer() {
        String rawStr = "R";
        
        try {
            FAAModeIndicator e = FAAModeIndicator.getFAAModeIndicator(rawStr);
            Assert.assertEquals("Wrong FAA mode", FAAModeIndicator.RTK_INTEGER_MODE, e);
            Assert.assertEquals("Incorrect raw mode", rawStr, e.getRawValue());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testSimulated() {
        String rawStr = "S";
        
        try {
            FAAModeIndicator e = FAAModeIndicator.getFAAModeIndicator(rawStr);
            Assert.assertEquals("Wrong FAA mode", FAAModeIndicator.SIMULATED_MODE, e);
            Assert.assertEquals("Incorrect raw mode", rawStr, e.getRawValue());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testUnsupportedError() {
        String rawStr = "ZZZZZZZZZZZ";
        
        try {
            try {
                FAAModeIndicator.getFAAModeIndicator(rawStr);
                Assert.fail("Should have thrown an exception for invalid raw value");
            } catch (RuntimeException e) {
                // Ignore expected exception
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }
}
