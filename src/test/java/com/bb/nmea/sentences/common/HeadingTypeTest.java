package com.bb.nmea.sentences.common;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

public class HeadingTypeTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testMagnetic() {
        String rawStr = "M";
        
        try {
            HeadingType val = HeadingType.getHeadingType(rawStr);
            Assert.assertEquals("Wrong heading type", HeadingType.MAGNETIC, val);
            Assert.assertEquals("Incorrect raw heading type", rawStr, val.getRawHeadingType());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }
}
