package com.bb.nmea.sentences.proprietary.furuno;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FEC_GPint_Test {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSingleRequest() {
        String rawStr = "$PFEC,GPint,ast01*13";
        
        try {
            long preTime = System.currentTimeMillis();
            FEC_GPint s = new FEC_GPint(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "PFEC", s.getTag());
            Assert.assertEquals("Invalid manufacterer ID", "FEC", s.getSupportedManufacturerID());
            Assert.assertEquals("Invalid manufacterer sentence ID", "GPint", s.getManufacturerSentenceId());
            Assert.assertEquals("Invalid sentence ID", "FEC-GPint", s.getSentenceId());
            Assert.assertEquals("Invalid checksum", "13", s.getChecksum());
            
            Assert.assertEquals("Invalid number of log requests", 1, s.getLogOutputIntervals().size());
            Assert.assertEquals("Invalid request 1", "ast01", s.getLogOutputIntervals().get(0));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testDoubleRequest() {
        String rawStr = "$PFEC,GPint,ast01,GSA05*13";
        
        try {
            long preTime = System.currentTimeMillis();
            FEC_GPint s = new FEC_GPint(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "PFEC", s.getTag());
            Assert.assertEquals("Invalid manufacterer ID", "FEC", s.getSupportedManufacturerID());
            Assert.assertEquals("Invalid manufacterer sentence ID", "GPint", s.getManufacturerSentenceId());
            Assert.assertEquals("Invalid sentence ID", "FEC-GPint", s.getSentenceId());
            Assert.assertEquals("Invalid checksum", "13", s.getChecksum());
            
            Assert.assertEquals("Invalid number of log requests", 2, s.getLogOutputIntervals().size());
            Assert.assertEquals("Invalid request 0", "ast01", s.getLogOutputIntervals().get(0));
            Assert.assertEquals("Invalid request 1", "GSA05", s.getLogOutputIntervals().get(1));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testFiveRequests() {
        String rawStr = "$PFEC,GPint,ast01,GSA05,tst08,std01,gpt60*13";
        
        try {
            long preTime = System.currentTimeMillis();
            FEC_GPint s = new FEC_GPint(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "PFEC", s.getTag());
            Assert.assertEquals("Invalid manufacterer ID", "FEC", s.getSupportedManufacturerID());
            Assert.assertEquals("Invalid manufacterer sentence ID", "GPint", s.getManufacturerSentenceId());
            Assert.assertEquals("Invalid sentence ID", "FEC-GPint", s.getSentenceId());
            Assert.assertEquals("Invalid checksum", "13", s.getChecksum());
            
            Assert.assertEquals("Invalid number of log requests", 5, s.getLogOutputIntervals().size());
            Assert.assertEquals("Invalid request 0", "ast01", s.getLogOutputIntervals().get(0));
            Assert.assertEquals("Invalid request 1", "GSA05", s.getLogOutputIntervals().get(1));
            Assert.assertEquals("Invalid request 2", "tst08", s.getLogOutputIntervals().get(2));
            Assert.assertEquals("Invalid request 3", "std01", s.getLogOutputIntervals().get(3));
            Assert.assertEquals("Invalid request 4", "gpt60", s.getLogOutputIntervals().get(4));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }
}
