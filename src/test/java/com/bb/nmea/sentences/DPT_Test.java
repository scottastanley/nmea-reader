package com.bb.nmea.sentences;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DPT_Test {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testMissingOffset() {
        String rawStr = "$SDDPT,5.1,*7D";
        
        try {
            long preTime = System.currentTimeMillis();
            DPT s = new DPT(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "SDDPT", s.getTag());
            Assert.assertEquals("Invalid talked ID", "SD", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "DPT", s.getTypeCode());
            Assert.assertEquals("Invalid checksum", "7D", s.getChecksum());
            
            Assert.assertEquals("Invalid depth below transducer", Float.valueOf(5.1f), s.getDepthBelowTransducer());
            Assert.assertEquals("Invalid traansducer offset", null, s.getTransducerOffset());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testPositiveOffset() {
        String rawStr = "$SDDPT,14.9,1.3*7D";
        
        try {
            long preTime = System.currentTimeMillis();
            DPT s = new DPT(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "SDDPT", s.getTag());
            Assert.assertEquals("Invalid talked ID", "SD", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "DPT", s.getTypeCode());
            Assert.assertEquals("Invalid checksum", "7D", s.getChecksum());
            
            Assert.assertEquals("Invalid depth below transducer", Float.valueOf(14.9f), s.getDepthBelowTransducer());
            Assert.assertEquals("Invalid traansducer offset", Float.valueOf(1.3f), s.getTransducerOffset());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testNegativeOffset() {
        String rawStr = "$SDDPT,32.5,-2.1*7D";
        
        try {
            long preTime = System.currentTimeMillis();
            DPT s = new DPT(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "SDDPT", s.getTag());
            Assert.assertEquals("Invalid talked ID", "SD", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "DPT", s.getTypeCode());
            Assert.assertEquals("Invalid checksum", "7D", s.getChecksum());
            
            Assert.assertEquals("Invalid depth below transducer", Float.valueOf(32.5f), s.getDepthBelowTransducer());
            Assert.assertEquals("Invalid traansducer offset", Float.valueOf(-2.1f), s.getTransducerOffset());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }
}
