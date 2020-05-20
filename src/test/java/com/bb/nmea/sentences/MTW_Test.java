package com.bb.nmea.sentences;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bb.nmea.sentences.common.TemperatureUnits;

import junit.framework.Assert;

public class MTW_Test {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testCase1() {
        String rawStr = "$VWMTW,13.0,C*10";
        
        try {
            long preTime = System.currentTimeMillis();
            MTW s = new MTW(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "VWMTW", s.getTag());
            Assert.assertEquals("Invalid talked ID", "VW", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "MTW", s.getTypeCode());
            Assert.assertEquals("Invalid checksum", "10", s.getChecksum());
            
            Assert.assertEquals("Invalid temperature", Float.valueOf(13.0f), s.getMeanWaterTemp());
            Assert.assertEquals("Invalid units", TemperatureUnits.CELCIUS, s.getUnits());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testCase2() {
        String rawStr = "$VWMTW,21.2,C*10";
        
        try {
            long preTime = System.currentTimeMillis();
            MTW s = new MTW(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "VWMTW", s.getTag());
            Assert.assertEquals("Invalid talked ID", "VW", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "MTW", s.getTypeCode());
            Assert.assertEquals("Invalid checksum", "10", s.getChecksum());
            
            Assert.assertEquals("Invalid temperature", Float.valueOf(21.2f), s.getMeanWaterTemp());
            Assert.assertEquals("Invalid units", TemperatureUnits.CELCIUS, s.getUnits());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testCase3() {
        String rawStr = "$VWMTW,32.1,C*10";
        
        try {
            long preTime = System.currentTimeMillis();
            MTW s = new MTW(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "VWMTW", s.getTag());
            Assert.assertEquals("Invalid talked ID", "VW", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "MTW", s.getTypeCode());
            Assert.assertEquals("Invalid checksum", "10", s.getChecksum());
            
            Assert.assertEquals("Invalid temperature", Float.valueOf(32.1f), s.getMeanWaterTemp());
            Assert.assertEquals("Invalid units", TemperatureUnits.CELCIUS, s.getUnits());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

}
