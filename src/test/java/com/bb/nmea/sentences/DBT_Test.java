package com.bb.nmea.sentences;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
            
            Assert.assertEquals("Invalid depth in feet", Float.valueOf(17.0f), s.getWaterDepthInFeet());
            Assert.assertEquals("Invalid depth in meters", Float.valueOf(5.1f), s.getWaterDepthInMeters());
            Assert.assertEquals("Invalid depth in fathoms", Float.valueOf(2.8f), s.getWaterDepthInFathoms());
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
            
            Assert.assertEquals("Invalid depth in feet", Float.valueOf(16.5f), s.getWaterDepthInFeet());
            Assert.assertEquals("Invalid depth in meters", Float.valueOf(5.0f), s.getWaterDepthInMeters());
            Assert.assertEquals("Invalid depth in fathoms", Float.valueOf(2.8f), s.getWaterDepthInFathoms());
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
            
            Assert.assertEquals("Invalid depth in feet", Float.valueOf(17.6f), s.getWaterDepthInFeet());
            Assert.assertEquals("Invalid depth in meters", Float.valueOf(5.3f), s.getWaterDepthInMeters());
            Assert.assertEquals("Invalid depth in fathoms", Float.valueOf(2.9f), s.getWaterDepthInFathoms());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }
}
