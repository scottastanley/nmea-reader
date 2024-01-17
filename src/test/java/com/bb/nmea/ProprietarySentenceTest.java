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
            TestProprietarySentence.setManufacturerSentenceId("CN");
            ProprietarySentence s = new TestProprietarySentence(rawStr);
            TestProprietarySentence.reset();
            long postTime = System.currentTimeMillis();
            
            Assert.assertTrue("Sentence should be valid", s.isValid());
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "PSMDCN", s.getTag());
            Assert.assertEquals("Invalid tag", "SMD", s.getSupportedManufacturerID());
            Assert.assertEquals("Invalid sentence ID", "SMD-CN", s.getSentenceId());
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
            TestProprietarySentence.setManufacturerSentenceId("GPint");
            ProprietarySentence s = new TestProprietarySentence(rawStr);
            TestProprietarySentence.reset();
            long postTime = System.currentTimeMillis();
            
            Assert.assertTrue("Sentence should be valid", s.isValid());
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "PFEC", s.getTag());
            Assert.assertEquals("Invalid tag", "FEC", s.getSupportedManufacturerID());
            Assert.assertEquals("Invalid sentence ID", "FEC-GPint", s.getSentenceId());
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
            TestProprietarySentence.setManufacturerSentenceId("idfnc");
            ProprietarySentence s = new TestProprietarySentence(rawStr);
            TestProprietarySentence.reset();
            long postTime = System.currentTimeMillis();
            
            Assert.assertTrue("Sentence should be valid", s.isValid());
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "PFEC", s.getTag());
            Assert.assertEquals("Invalid tag", "FEC", s.getSupportedManufacturerID());
            Assert.assertEquals("Invalid sentence ID", "FEC-idfnc", s.getSentenceId());
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

    @Test
    public void testNullManufactorerSentenceID() {
        String rawStr = "$PFEC,idfnc,A,1*2A";
        
        try {
            Assert.assertTrue("Message should be valid", NMEASentence.isValidRawSentence(rawStr));

            long preTime = System.currentTimeMillis();
            TestProprietarySentence.setManufacturerSentenceId(null);
            ProprietarySentence s = new TestProprietarySentence(rawStr);
            TestProprietarySentence.reset();
            long postTime = System.currentTimeMillis();
            
            Assert.assertTrue("Sentence should be valid", s.isValid());
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "PFEC", s.getTag());
            Assert.assertEquals("Invalid tag", "FEC", s.getSupportedManufacturerID());
            Assert.assertEquals("Invalid sentence ID", "FEC-null", s.getSentenceId());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }
    
    @Test
    public void testIsProprietarySentence() {
        String rawProprietaryStr = "$PFEC,idfnc,A,1*2A";
        String rawTalkerStr = "$GPDPT,2.6,-0.8*76";
        
        try {
            Assert.assertTrue("Should be proprietary", ProprietarySentence.isProprietarySentence(rawProprietaryStr));
            Assert.assertFalse("Should not be a proprietary", ProprietarySentence.isProprietarySentence(rawTalkerStr));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }
}
