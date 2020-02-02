package com.bb.nmea.sentences;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bb.nmea.HeadingType;

import junit.framework.Assert;

public class HDM_Test {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testCase1() {
        String rawStr = "$APHDM,345.5,M*34";
        
        try {
            long preTime = System.currentTimeMillis();
            HDM s = new HDM(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "APHDM", s.getTag());
            Assert.assertEquals("Invalid talked ID", "AP", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "HDM", s.getTypeCode());
            Assert.assertEquals("Invalid checksum", "34", s.getChecksum());
            
            Assert.assertEquals("Invalid heading", new Float(345.5), s.getHeadingDegrees());
            Assert.assertEquals("Invalid heading type", HeadingType.MAGNETIC, s.getHeadingType());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testCase2() {
        String rawStr = "$APHDM,344.8,M*38";
        
        try {
            long preTime = System.currentTimeMillis();
            HDM s = new HDM(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "APHDM", s.getTag());
            Assert.assertEquals("Invalid talked ID", "AP", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "HDM", s.getTypeCode());
            Assert.assertEquals("Invalid checksum", "38", s.getChecksum());
            
            Assert.assertEquals("Invalid heading", new Float(344.8), s.getHeadingDegrees());
            Assert.assertEquals("Invalid heading type", HeadingType.MAGNETIC, s.getHeadingType());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }
}
