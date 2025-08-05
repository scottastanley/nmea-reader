package com.bb.nmea.sentences.proprietary.furuno;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FEC_idfnc_Test {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCase1() {
        String rawStr = "$PFEC,idfnc,A,1*2A"; 
        
        try {
            long preTime = System.currentTimeMillis();
            FEC_idfnc s = new FEC_idfnc(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "PFEC", s.getTag());
            Assert.assertEquals("Invalid manufacterer ID", "FEC", s.getSupportedManufacturerID());
            Assert.assertEquals("Invalid manufacterer sentence ID", "idfnc", s.getManufacturerSentenceId());
            Assert.assertEquals("Invalid sentence ID", "FEC-idfnc", s.getSentenceId());
            Assert.assertEquals("Invalid checksum", "2A", s.getChecksum());
            
            Assert.assertEquals("Invalid Field 1", "A", s.getField1());
            Assert.assertEquals("Invalid Field 2", Float.valueOf(1), s.getField2());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
	}


	@Test
	public void testCase2() {
        String rawStr = "$PFEC,idfnc,R,*08"; 
        
        try {
            long preTime = System.currentTimeMillis();
            FEC_idfnc s = new FEC_idfnc(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "PFEC", s.getTag());
            Assert.assertEquals("Invalid manufacterer ID", "FEC", s.getSupportedManufacturerID());
            Assert.assertEquals("Invalid manufacterer sentence ID", "idfnc", s.getManufacturerSentenceId());
            Assert.assertEquals("Invalid sentence ID", "FEC-idfnc", s.getSentenceId());
            Assert.assertEquals("Invalid checksum", "08", s.getChecksum());
            
            Assert.assertEquals("Invalid Field 1", "R", s.getField1());
            Assert.assertNull("Invalid Field 2", s.getField2());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
	}
}
