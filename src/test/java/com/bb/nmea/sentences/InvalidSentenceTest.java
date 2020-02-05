package com.bb.nmea.sentences;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

public class InvalidSentenceTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
    @Test
    public void testAPHDT_InvalidChecksum() {
        String rawStr = "$APHDT,357.9,T*31";
        
        try {
            InvalidSentence s = new InvalidSentence(rawStr);
            Assert.assertFalse("Message should be invalid", s.isValid());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testParseAPHDG_InvalidChecksum() {
        String rawStr = "$APHDG,257.0,,,13.0,E*08";
        
        try {
            InvalidSentence s = new InvalidSentence(rawStr);
            Assert.assertFalse("Message should be invalid", s.isValid());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }
}
