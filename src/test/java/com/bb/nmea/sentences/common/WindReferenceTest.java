package com.bb.nmea.sentences.common;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class WindReferenceTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testRelative() {
        String rawValue = "R";
        
        try {
            WindReference e = WindReference.getWindReference(rawValue);
            Assert.assertEquals("Wrong units", WindReference.RELATIVE, e);
            Assert.assertEquals("Incorrect raw units", rawValue, e.getRawValue());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testTrue() {
        String rawValue = "T";
        
        try {
            WindReference e = WindReference.getWindReference(rawValue);
            Assert.assertEquals("Wrong units", WindReference.TRUE, e);
            Assert.assertEquals("Incorrect raw units", rawValue, e.getRawValue());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }
}
