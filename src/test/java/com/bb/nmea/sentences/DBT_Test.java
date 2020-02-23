package com.bb.nmea.sentences;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

public class DBT_Test {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testCase1() {
        String rawStr = "$SDDBT,17.0,f,5.1,M,2.8,F*3E";
        
        try {
            long preTime = System.currentTimeMillis();
            DBT s = new DBT(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "SDDBT", s.getTag());
            Assert.assertEquals("Invalid talked ID", "SD", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "DBT", s.getTypeCode());
            Assert.assertEquals("Invalid checksum", "3E", s.getChecksum());
            
            Assert.assertEquals("Invalid depth in feet", new Float(17.0), s.getWaterDepthInFeet());
            Assert.assertEquals("Invalid depth in meters", new Float(5.1), s.getWaterDepthInMeters());
            Assert.assertEquals("Invalid depth in fathoms", new Float(2.8), s.getWaterDepthInFathoms());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testCase2() {
        String rawStr = "$SDDBT,16.5,f,5.0,M,2.8,F*3B";
        
        try {
            long preTime = System.currentTimeMillis();
            DBT s = new DBT(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "SDDBT", s.getTag());
            Assert.assertEquals("Invalid talked ID", "SD", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "DBT", s.getTypeCode());
            Assert.assertEquals("Invalid checksum", "3B", s.getChecksum());
            
            Assert.assertEquals("Invalid depth in feet", new Float(16.5), s.getWaterDepthInFeet());
            Assert.assertEquals("Invalid depth in meters", new Float(5.0), s.getWaterDepthInMeters());
            Assert.assertEquals("Invalid depth in fathoms", new Float(2.8), s.getWaterDepthInFathoms());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testCase3() {
        String rawStr = "$SDDBT,17.6,f,5.3,M,2.9,F*3B";
        
        try {
            long preTime = System.currentTimeMillis();
            DBT s = new DBT(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "SDDBT", s.getTag());
            Assert.assertEquals("Invalid talked ID", "SD", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "DBT", s.getTypeCode());
            Assert.assertEquals("Invalid checksum", "3B", s.getChecksum());
            
            Assert.assertEquals("Invalid depth in feet", new Float(17.6), s.getWaterDepthInFeet());
            Assert.assertEquals("Invalid depth in meters", new Float(5.3), s.getWaterDepthInMeters());
            Assert.assertEquals("Invalid depth in fathoms", new Float(2.9), s.getWaterDepthInFathoms());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }
}
