package com.bb.nmea.sentences.proprietary.shipmodul;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SMD_CNTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testChannel1() {
        String rawStr = "$PSMDCN,1*1A"; 
        
        try {
            long preTime = System.currentTimeMillis();
            SMD_CN s = new SMD_CN(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "PSMDCN", s.getTag());
            Assert.assertEquals("Invalid manufacterer ID", "SMD", s.getSupportedManufacturerID());
            Assert.assertEquals("Invalid manufacterer sentence ID", "CN", s.getManufacturerSentenceId());
            Assert.assertEquals("Invalid sentence ID", "SMD-CN", s.getSentenceId());
            Assert.assertEquals("Invalid checksum", "1A", s.getChecksum());
            
            Assert.assertEquals("Invalid channel", Integer.valueOf(1), s.getChannel());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

	@Test
	public void testChannel2() {
        String rawStr = "$PSMDCN,2*19"; 
        
        try {
            long preTime = System.currentTimeMillis();
            SMD_CN s = new SMD_CN(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "PSMDCN", s.getTag());
            Assert.assertEquals("Invalid manufacterer ID", "SMD", s.getSupportedManufacturerID());
            Assert.assertEquals("Invalid manufacterer sentence ID", "CN", s.getManufacturerSentenceId());
            Assert.assertEquals("Invalid sentence ID", "SMD-CN", s.getSentenceId());
            Assert.assertEquals("Invalid checksum", "19", s.getChecksum());
            
            Assert.assertEquals("Invalid channel", Integer.valueOf(2), s.getChannel());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

	@Test
	public void testChannel3() {
        String rawStr = "$PSMDCN,3*18"; 
        
        try {
            long preTime = System.currentTimeMillis();
            SMD_CN s = new SMD_CN(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "PSMDCN", s.getTag());
            Assert.assertEquals("Invalid manufacterer ID", "SMD", s.getSupportedManufacturerID());
            Assert.assertEquals("Invalid manufacterer sentence ID", "CN", s.getManufacturerSentenceId());
            Assert.assertEquals("Invalid sentence ID", "SMD-CN", s.getSentenceId());
            Assert.assertEquals("Invalid checksum", "18", s.getChecksum());
            
            Assert.assertEquals("Invalid channel", Integer.valueOf(3), s.getChannel());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

	@Test
	public void testChannel4() {
        String rawStr = "$PSMDCN,4*1F"; 
        
        try {
            long preTime = System.currentTimeMillis();
            SMD_CN s = new SMD_CN(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "PSMDCN", s.getTag());
            Assert.assertEquals("Invalid manufacterer ID", "SMD", s.getSupportedManufacturerID());
            Assert.assertEquals("Invalid manufacterer sentence ID", "CN", s.getManufacturerSentenceId());
            Assert.assertEquals("Invalid sentence ID", "SMD-CN", s.getSentenceId());
            Assert.assertEquals("Invalid checksum", "1F", s.getChecksum());
            
            Assert.assertEquals("Invalid channel", Integer.valueOf(4), s.getChannel());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }
}
