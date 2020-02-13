package com.bb.nmea.sentences.common;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

public class LatLongTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testCase1() {
        String rawLatLong = "3747.151";
        Integer degrees = new Integer(37);
        Float minutes = new Float(47.151);
        
        try {
            LatLong l = new LatLong(rawLatLong);
            
            Assert.assertEquals("Wrong degrees", degrees, l.getDegrees());
            Assert.assertEquals("Wrong minutes", minutes, l.getMinutes());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testCase2() {
        String rawLatLong = "12223.253";
        Integer degrees = new Integer(122);
        Float minutes = new Float(23.253);
        
        try {
            LatLong l = new LatLong(rawLatLong);
            
            Assert.assertEquals("Wrong degrees", degrees, l.getDegrees());
            Assert.assertEquals("Wrong minutes", minutes, l.getMinutes());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testCase3() {
        String rawLatLong = "16708.033";
        Integer degrees = new Integer(167);
        Float minutes = new Float(8.033);
        
        try {
            LatLong l = new LatLong(rawLatLong);
            
            Assert.assertEquals("Wrong degrees", degrees, l.getDegrees());
            Assert.assertEquals("Wrong minutes", minutes, l.getMinutes());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testCase4() {
        String rawLatLong = "4533.35";
        Integer degrees = new Integer(45);
        Float minutes = new Float(33.35);
        
        try {
            LatLong l = new LatLong(rawLatLong);
            
            Assert.assertEquals("Wrong degrees", degrees, l.getDegrees());
            Assert.assertEquals("Wrong minutes", minutes, l.getMinutes());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }
}
