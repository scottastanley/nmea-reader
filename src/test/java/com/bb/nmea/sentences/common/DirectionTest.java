package com.bb.nmea.sentences.common;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DirectionTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testNorth() {
        String rawStr = "N";
        
        try {
            Direction e = Direction.getDirection(rawStr);
            Assert.assertEquals("Wrong direction", Direction.NORTH, e);
            Assert.assertEquals("Incorrect raw direction", rawStr, e.getRawDirection());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testSouth() {
        String rawStr = "S";
        
        try {
            Direction e = Direction.getDirection(rawStr);
            Assert.assertEquals("Wrong direction", Direction.SOUTH, e);
            Assert.assertEquals("Incorrect raw direction", rawStr, e.getRawDirection());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testEast() {
        String rawStr = "E";
        
        try {
            Direction e = Direction.getDirection(rawStr);
            Assert.assertEquals("Wrong direction", Direction.EAST, e);
            Assert.assertEquals("Incorrect raw direction", rawStr, e.getRawDirection());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testWest() {
        String rawStr = "W";
        
        try {
            Direction e = Direction.getDirection(rawStr);
            Assert.assertEquals("Wrong direction", Direction.WEST, e);
            Assert.assertEquals("Incorrect raw direction", rawStr, e.getRawDirection());
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
                Direction.getDirection(rawStr);
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
