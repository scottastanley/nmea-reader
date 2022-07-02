package com.bb.nmea;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ProprietarySentenceTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testParsePSMDCN() {
        String rawStr = "$PSMDCN,1*1A";
        
        try {
            Assert.assertTrue("Message should be valid", NMEASentence.isValidRawSentence(rawStr));

            long preTime = System.currentTimeMillis();
            ProprietarySentence s = new TestProprietarySentence(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertTrue("Sentence should be valid", s.isValid());
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "PSMDCN", s.getTag());
            Assert.assertEquals("Invalid type code", "SMDCN", s.getSentenceId());
            Assert.assertEquals("Invalid checksum", "1A", s.getChecksum());
            Assert.assertEquals("Incorrect number of fields", Integer.valueOf(2), s.getNumFields());
            
            // Validate raw fields
            Assert.assertEquals("Invalid field 0 string", "PSMDCN", s.getField(0));
            
            Assert.assertEquals("Invalid field 1 string", "1", s.getField(1));
            Assert.assertEquals("Invalid field 1 int", Integer.valueOf(1), s.getFieldAsInteger(1));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testParsePFEC_A() {
        String rawStr = "$PFEC,GPint,ast01*13";
        
        try {
            Assert.assertTrue("Message should be valid", NMEASentence.isValidRawSentence(rawStr));

            long preTime = System.currentTimeMillis();
            ProprietarySentence s = new TestProprietarySentence(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertTrue("Sentence should be valid", s.isValid());
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "PFEC", s.getTag());
            Assert.assertEquals("Invalid type code", "FEC", s.getSentenceId());
            Assert.assertEquals("Invalid checksum", "13", s.getChecksum());
            Assert.assertEquals("Incorrect number of fields", Integer.valueOf(3), s.getNumFields());
            
            // Validate raw fields
            Assert.assertEquals("Invalid field 0 string", "PFEC", s.getField(0));
            Assert.assertEquals("Invalid field 1 string", "GPint", s.getField(1));
            Assert.assertEquals("Invalid field 2 string", "ast01", s.getField(2));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testParsePFEC_B() {
        String rawStr = "$PFEC,idfnc,A,1*2A";
        
        try {
            Assert.assertTrue("Message should be valid", NMEASentence.isValidRawSentence(rawStr));

            long preTime = System.currentTimeMillis();
            ProprietarySentence s = new TestProprietarySentence(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertTrue("Sentence should be valid", s.isValid());
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "PFEC", s.getTag());
            Assert.assertEquals("Invalid type code", "FEC", s.getSentenceId());
            Assert.assertEquals("Invalid checksum", "2A", s.getChecksum());
            Assert.assertEquals("Incorrect number of fields", Integer.valueOf(4), s.getNumFields());
            
            // Validate raw fields
            Assert.assertEquals("Invalid field 0 string", "PFEC", s.getField(0));
            Assert.assertEquals("Invalid field 1 string", "idfnc", s.getField(1));
            Assert.assertEquals("Invalid field 2 string", "A", s.getField(2));
            Assert.assertEquals("Invalid field 3 string", "1", s.getField(3));
            Assert.assertEquals("Invalid field 3 int", Integer.valueOf(1), s.getFieldAsInteger(3));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }
}
