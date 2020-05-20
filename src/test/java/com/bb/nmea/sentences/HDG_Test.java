package com.bb.nmea.sentences;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bb.nmea.sentences.common.Direction;

import junit.framework.Assert;

public class HDG_Test {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testFullyPopulated() {
        String rawStr = "$APHDG,252.1,5.3,N,13.0,E*05";
        
        try {
            long preTime = System.currentTimeMillis();
            HDG s = new HDG(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "APHDG", s.getTag());
            Assert.assertEquals("Invalid talked ID", "AP", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "HDG", s.getTypeCode());
            Assert.assertEquals("Invalid checksum", "05", s.getChecksum());
            
            Assert.assertEquals("Invalid heading", Float.valueOf(252.1f), s.getHeadingDegrees());
            Assert.assertEquals("Invalid deviation degrees", Float.valueOf(5.3f), s.getDeviationDegrees());
            Assert.assertEquals("Invalid deviation direction", Direction.NORTH, s.getDeviationDirection());
            Assert.assertEquals("Invalid variation degrees", Float.valueOf(13.0f), s.getVariationDegrees());
            Assert.assertEquals("Invalid variation direction", Direction.EAST, s.getVariationDirection());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testMissingDeviation() {
        String rawStr = "$APHDG,252.1,,,13.0,E*05";
        
        try {
            long preTime = System.currentTimeMillis();
            HDG s = new HDG(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "APHDG", s.getTag());
            Assert.assertEquals("Invalid talked ID", "AP", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "HDG", s.getTypeCode());
            Assert.assertEquals("Invalid checksum", "05", s.getChecksum());
            
            Assert.assertEquals("Invalid heading", Float.valueOf(252.1f), s.getHeadingDegrees());
            Assert.assertEquals("Invalid deviation degrees", null, s.getDeviationDegrees());
            Assert.assertEquals("Invalid deviation direction", null, s.getDeviationDirection());
            Assert.assertEquals("Invalid variation degrees", Float.valueOf(13.0f), s.getVariationDegrees());
            Assert.assertEquals("Invalid variation direction", Direction.EAST, s.getVariationDirection());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testMissingVariation() {
        String rawStr = "$APHDG,252.1,15.1,S,,*05";
        
        try {
            long preTime = System.currentTimeMillis();
            HDG s = new HDG(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "APHDG", s.getTag());
            Assert.assertEquals("Invalid talked ID", "AP", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "HDG", s.getTypeCode());
            Assert.assertEquals("Invalid checksum", "05", s.getChecksum());
            
            Assert.assertEquals("Invalid heading", Float.valueOf(252.1f), s.getHeadingDegrees());
            Assert.assertEquals("Invalid deviation degrees", Float.valueOf(15.1f), s.getDeviationDegrees());
            Assert.assertEquals("Invalid deviation direction", Direction.SOUTH, s.getDeviationDirection());
            Assert.assertEquals("Invalid variation degrees", null, s.getVariationDegrees());
            Assert.assertEquals("Invalid variation direction", null, s.getVariationDirection());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

}
