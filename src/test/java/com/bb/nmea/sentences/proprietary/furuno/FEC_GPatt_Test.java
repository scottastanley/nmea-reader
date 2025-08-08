package com.bb.nmea.sentences.proprietary.furuno;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FEC_GPatt_Test {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCase1() {
        String rawStr = "$PFEC,GPatt,189.2,-1.1,-0.9*43"; 
        
        try {
            long preTime = System.currentTimeMillis();
            FEC_GPatt s = new FEC_GPatt(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "PFEC", s.getTag());
            Assert.assertEquals("Invalid manufacterer ID", "FEC", s.getSupportedManufacturerID());
            Assert.assertEquals("Invalid manufacterer sentence ID", "GPatt", s.getManufacturerSentenceId());
            Assert.assertEquals("Invalid sentence ID", "FEC-GPatt", s.getSentenceId());
            Assert.assertEquals("Invalid checksum", "43", s.getChecksum());
            
            Assert.assertEquals("Invalid heading", Float.valueOf(189.2F), s.getTrueHeading());
            Assert.assertEquals("Invalid pitch", Float.valueOf(-1.1F), s.getPitchAngleDeg());
            Assert.assertEquals("Invalid roll", Float.valueOf(-0.9F), s.getRollAngleDeg());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
	}

	@Test
	public void testCase2() {
        String rawStr = "$PFEC,GPatt,271.5,-1.1,-0.8*41"; 
        
        try {
            long preTime = System.currentTimeMillis();
            FEC_GPatt s = new FEC_GPatt(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "PFEC", s.getTag());
            Assert.assertEquals("Invalid manufacterer ID", "FEC", s.getSupportedManufacturerID());
            Assert.assertEquals("Invalid manufacterer sentence ID", "GPatt", s.getManufacturerSentenceId());
            Assert.assertEquals("Invalid sentence ID", "FEC-GPatt", s.getSentenceId());
            Assert.assertEquals("Invalid checksum", "41", s.getChecksum());
            
            Assert.assertEquals("Invalid heading", Float.valueOf(271.5F), s.getTrueHeading());
            Assert.assertEquals("Invalid pitch", Float.valueOf(-1.1F), s.getPitchAngleDeg());
            Assert.assertEquals("Invalid roll", Float.valueOf(-0.8F), s.getRollAngleDeg());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
	}

	@Test
	public void testCase_missingHeading() {
        String rawStr = "$PFEC,GPatt,,-1.2,-0.9*6C"; 
        
        try {
            long preTime = System.currentTimeMillis();
            FEC_GPatt s = new FEC_GPatt(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "PFEC", s.getTag());
            Assert.assertEquals("Invalid manufacterer ID", "FEC", s.getSupportedManufacturerID());
            Assert.assertEquals("Invalid manufacterer sentence ID", "GPatt", s.getManufacturerSentenceId());
            Assert.assertEquals("Invalid sentence ID", "FEC-GPatt", s.getSentenceId());
            Assert.assertEquals("Invalid checksum", "6C", s.getChecksum());
            
            Assert.assertNull("Invalid heading", s.getTrueHeading());
            Assert.assertEquals("Invalid pitch", Float.valueOf(-1.2F), s.getPitchAngleDeg());
            Assert.assertEquals("Invalid roll", Float.valueOf(-0.9F), s.getRollAngleDeg());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
	}
}
