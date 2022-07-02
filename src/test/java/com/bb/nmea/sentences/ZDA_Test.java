package com.bb.nmea.sentences;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ZDA_Test {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testFullyPopulatedNegativeOffset() {
        String rawStr = "$GPZDA,212340.43,10,05,2020,-7,-30*63";
        
        try {
            long preTime = System.currentTimeMillis();
            ZDA s = new ZDA(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "GPZDA", s.getTag());
            Assert.assertEquals("Invalid talked ID", "GP", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "ZDA", s.getTypeCode());
            Assert.assertEquals("Invalid checksum", "63", s.getChecksum());
            
            Assert.assertEquals("Invalid hours", Integer.valueOf(21), s.getHours());
            Assert.assertEquals("Invalid minutes", Integer.valueOf(23), s.getMinutes());
            Assert.assertEquals("Invalid seconds", Float.valueOf(40.43f), s.getSeconds());
            Assert.assertEquals("Invalid day", Integer.valueOf(10), s.getDay());
            Assert.assertEquals("Invalid month", Integer.valueOf(5), s.getMonth());
            Assert.assertEquals("Invalid year", Integer.valueOf(2020), s.getYear());
            Assert.assertEquals("Invalid local time hour offset", Integer.valueOf(-7), s.getLocalZoneDescription());
            Assert.assertEquals("Invalid local time minute offset", Integer.valueOf(-30), s.getLocalZoneMinutes());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testFullyPopulatedPositiveOffset() {
        String rawStr = "$GPZDA,212340.43,10,05,2020,12,59*63";
        
        try {
            long preTime = System.currentTimeMillis();
            ZDA s = new ZDA(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "GPZDA", s.getTag());
            Assert.assertEquals("Invalid talked ID", "GP", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "ZDA", s.getTypeCode());
            Assert.assertEquals("Invalid checksum", "63", s.getChecksum());
            
            Assert.assertEquals("Invalid hours", Integer.valueOf(21), s.getHours());
            Assert.assertEquals("Invalid minutes", Integer.valueOf(23), s.getMinutes());
            Assert.assertEquals("Invalid seconds", Float.valueOf(40.43f), s.getSeconds());
            Assert.assertEquals("Invalid day", Integer.valueOf(10), s.getDay());
            Assert.assertEquals("Invalid month", Integer.valueOf(5), s.getMonth());
            Assert.assertEquals("Invalid year", Integer.valueOf(2020), s.getYear());
            Assert.assertEquals("Invalid local time hour offset", Integer.valueOf(12), s.getLocalZoneDescription());
            Assert.assertEquals("Invalid local time minute offset", Integer.valueOf(59), s.getLocalZoneMinutes());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testMissingLocalTimeOffsets() {
        String rawStr = "$GPZDA,212340.43,10,05,2020,,*63";
        
        try {
            long preTime = System.currentTimeMillis();
            ZDA s = new ZDA(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "GPZDA", s.getTag());
            Assert.assertEquals("Invalid talked ID", "GP", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "ZDA", s.getTypeCode());
            Assert.assertEquals("Invalid checksum", "63", s.getChecksum());
            
            Assert.assertEquals("Invalid hours", Integer.valueOf(21), s.getHours());
            Assert.assertEquals("Invalid minutes", Integer.valueOf(23), s.getMinutes());
            Assert.assertEquals("Invalid seconds", Float.valueOf(40.43f), s.getSeconds());
            Assert.assertEquals("Invalid day", Integer.valueOf(10), s.getDay());
            Assert.assertEquals("Invalid month", Integer.valueOf(5), s.getMonth());
            Assert.assertEquals("Invalid year", Integer.valueOf(2020), s.getYear());
            Assert.assertEquals("Invalid local time hour offset", null, s.getLocalZoneDescription());
            Assert.assertEquals("Invalid local time minute offset", null, s.getLocalZoneMinutes());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }
}
