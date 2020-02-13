package com.bb.nmea.sentences.common;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

public class StatusTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testValid() {
        String rawStatus = "A";
        
        try {
            Status s = Status.getStatus(rawStatus);
            Assert.assertEquals("Wrong status", Status.VALID, s);
            Assert.assertEquals("Incorrect raw status", rawStatus, s.getRawStatus());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testInvalid() {
        String rawStatus = "V";
        
        try {
            Status s = Status.getStatus(rawStatus);
            Assert.assertEquals("Wrong status", Status.INVALID, s);
            Assert.assertEquals("Incorrect raw status", rawStatus, s.getRawStatus());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testAnythingElse() {
        String rawStatus = "LFKDSLFKJDSLKF";
        
        try {
            Status s = Status.getStatus(rawStatus);
            Assert.assertEquals("Wrong status", Status.INVALID, s);
            Assert.assertEquals("Incorrect raw status", "V", s.getRawStatus());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }
}
