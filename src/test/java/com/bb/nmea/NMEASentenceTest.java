package com.bb.nmea;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

public class NMEASentenceTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testInvalidMissing$() {
        String rawStr = "APRSA,8.4,A*32";
        
        try {
            NMEASentence s = new TestNMEASentence(rawStr);
            
            Assert.assertEquals("Invalid raw sentence", rawStr, s.getRawSentence());
            Assert.assertFalse("Should be invalid", s.getIsValid());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testInvalidMissingChecksum() {
        String rawStr = "$APRSA,8.4,A";
        
        try {
            NMEASentence s = new TestNMEASentence(rawStr);
            
            Assert.assertEquals("Invalid raw sentence", rawStr, s.getRawSentence());
            Assert.assertFalse("Should be invalid", s.getIsValid());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testInvalidInvalidChecksumLength() {
        String rawStr = "$APRSA,8.4,A*3";
        
        try {
            NMEASentence s = new TestNMEASentence(rawStr);
            
            Assert.assertEquals("Invalid raw sentence", rawStr, s.getRawSentence());
            Assert.assertFalse("Should be invalid", s.getIsValid());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testInvalidTag_lt5chars() {
        String rawStr = "$AHDT,357.3,T*31";
        
        try {
            NMEASentence s = new TestNMEASentence(rawStr);
            
            Assert.assertFalse("Should not be valid", s.getIsValid());
            
            Assert.assertEquals("Invalid tag", "AHDT", s.getTag());
            Assert.assertNull("Invalid talked ID", s.getTalkerId());
            Assert.assertNull("Invalid type code", s.getTypeCode());
            Assert.assertEquals("Invalid checksum", "31", s.getChecksum());
            
            // Validate raw fields
            Assert.assertNull("Invalid field 0 string", s.getField(0));
            
            Assert.assertNull("Invalid field 1 string", s.getField(1));
            Assert.assertNull("Invalid field 1 float", s.getFieldAsFloat(1));
            
            Assert.assertNull("Invalid field 2 string", s.getField(2));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testParseAPHDT() {
        String rawStr = "$APHDT,357.3,T*31";
        
        try {
            NMEASentence s = new TestNMEASentence(rawStr);
            
            Assert.assertTrue("Should be valid", s.getIsValid());
            
            Assert.assertEquals("Invalid tag", "APHDT", s.getTag());
            Assert.assertEquals("Invalid talked ID", "AP", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "HDT", s.getTypeCode());
            Assert.assertEquals("Invalid checksum", "31", s.getChecksum());
            
            // Validate raw fields
            Assert.assertEquals("Invalid field 0 string", "APHDT", s.getField(0));
            
            Assert.assertEquals("Invalid field 1 string", "357.3", s.getField(1));
            Assert.assertEquals("Invalid field 1 float", new Float(357.3), s.getFieldAsFloat(1));
            
            Assert.assertEquals("Invalid field 2 string", "T", s.getField(2));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testParseAPHDT_leadingBang() {
        String rawStr = "!APHDT,357.3,T*31";
        
        try {
            NMEASentence s = new TestNMEASentence(rawStr);
            
            Assert.assertTrue("Should be valid", s.getIsValid());
            
            Assert.assertEquals("Invalid tag", "APHDT", s.getTag());
            Assert.assertEquals("Invalid talked ID", "AP", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "HDT", s.getTypeCode());
            Assert.assertEquals("Invalid checksum", "31", s.getChecksum());
            
            // Validate raw fields
            Assert.assertEquals("Invalid field 0 string", "APHDT", s.getField(0));
            
            Assert.assertEquals("Invalid field 1 string", "357.3", s.getField(1));
            Assert.assertEquals("Invalid field 1 float", new Float(357.3), s.getFieldAsFloat(1));
            
            Assert.assertEquals("Invalid field 2 string", "T", s.getField(2));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }
    
    @Test
    public void testParseAPHDT_InvalidChecksum() {
        String rawStr = "$APHDT,357.9,T*31";
        
        try {
            NMEASentence s = new TestNMEASentence(rawStr);
            
            Assert.assertFalse("Should not be valid", s.getIsValid());
            
            Assert.assertEquals("Invalid tag", "APHDT", s.getTag());
            Assert.assertNull("Invalid talked ID", s.getTalkerId());
            Assert.assertNull("Invalid type code", s.getTypeCode());
            Assert.assertEquals("Invalid checksum", "31", s.getChecksum());
            
            // Validate raw fields
            Assert.assertNull("Invalid field 0 string", s.getField(0));
            
            Assert.assertNull("Invalid field 1 string", s.getField(1));
            Assert.assertNull("Invalid field 1 float", s.getFieldAsFloat(1));
            
            Assert.assertNull("Invalid field 2 string", s.getField(2));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testParseGPRMC() {
        String rawStr = "$GPRMC,065830,A,3746.830,N,12223.251,W,0.0,000.0,291219,013.0,E,A*08";
        
        try {
            NMEASentence s = new TestNMEASentence(rawStr);
            
            Assert.assertTrue("Should be valid", s.getIsValid());
            
            Assert.assertEquals("Invalid tag", "GPRMC", s.getTag());
            Assert.assertEquals("Invalid talked ID", "GP", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "RMC", s.getTypeCode());
            Assert.assertEquals("Invalid checksum", "08", s.getChecksum());
            
            // Validate raw fields
            Assert.assertEquals("Invalid field 0 string", "GPRMC", s.getField(0));
            
            Assert.assertEquals("Invalid field 1 string", "065830", s.getField(1));
            Assert.assertEquals("Invalid field 1 float", new Integer(65830), s.getFieldAsInteger(1));
            
            Assert.assertEquals("Invalid field 2 string", "A", s.getField(2));
            
            Assert.assertEquals("Invalid field 3 string", "3746.830", s.getField(3));
            Assert.assertEquals("Invalid field 3 string", new Float(3746.830), s.getFieldAsFloat(3));
            LatLongTest.validateLatLong("Invalid field 3 LatLong", 37, 46.83F, s.getFieldAsLatLong(3));
            
            Assert.assertEquals("Invalid field 4 string", "N", s.getField(4));
            
            Assert.assertEquals("Invalid field 5 string", "12223.251", s.getField(5));
            Assert.assertEquals("Invalid field 5 string", new Float(12223.251), s.getFieldAsFloat(5));
            LatLongTest.validateLatLong("Invalid field 5 LatLong", 122, 23.251F, s.getFieldAsLatLong(5));
            
            Assert.assertEquals("Invalid field 6 string", "W", s.getField(6));
            
            Assert.assertEquals("Invalid field 7 string", "0.0", s.getField(7));
            Assert.assertEquals("Invalid field 7 string", new Float(0.0), s.getFieldAsFloat(7));
            
            Assert.assertEquals("Invalid field 8 string", "000.0", s.getField(8));
            Assert.assertEquals("Invalid field 8 string", new Float(000.0), s.getFieldAsFloat(8));
            
            Assert.assertEquals("Invalid field 9 float", new Integer(291219), s.getFieldAsInteger(9));
            
            Assert.assertEquals("Invalid field 10 string", "013.0", s.getField(10));
            Assert.assertEquals("Invalid field 10 string", new Float(013.0), s.getFieldAsFloat(10));
            
            Assert.assertEquals("Invalid field 11 string", "E", s.getField(11));
            
            Assert.assertEquals("Invalid field 12 string", "A", s.getField(12));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testParseGPRMC_InvalidChecksum() {
        String rawStr = "$GPRMC,065830,A,3746.830,N,13523.251,W,0.0,000.0,291219,013.0,E,A*08";
        
        try {
            NMEASentence s = new TestNMEASentence(rawStr);
            
            Assert.assertFalse("Should not be valid", s.getIsValid());
            
            Assert.assertEquals("Invalid tag", "GPRMC", s.getTag());
            Assert.assertNull("Invalid talked ID", s.getTalkerId());
            Assert.assertNull("Invalid type code", s.getTypeCode());
            Assert.assertEquals("Invalid checksum", "08", s.getChecksum());
            
            // Validate raw fields
            Assert.assertNull("Invalid field 0 string", s.getField(0));
            
            Assert.assertNull("Invalid field 1 string", s.getField(1));
            Assert.assertNull("Invalid field 1 float", s.getFieldAsInteger(1));
            
            Assert.assertNull("Invalid field 2 string", s.getField(2));
            
            Assert.assertNull("Invalid field 3 string", s.getField(3));
            Assert.assertNull("Invalid field 3 string", s.getFieldAsFloat(3));
            Assert.assertNull("Invalid field 3 LatLong", s.getFieldAsLatLong(3));
            
            Assert.assertNull("Invalid field 4 string", s.getField(4));
            
            Assert.assertNull("Invalid field 5 string", s.getField(5));
            Assert.assertNull("Invalid field 5 string", s.getFieldAsFloat(5));
            Assert.assertNull("Invalid field 5 LatLong", s.getFieldAsLatLong(5));
            
            Assert.assertNull("Invalid field 6 string", s.getField(6));
            
            Assert.assertNull("Invalid field 7 string", s.getField(7));
            Assert.assertNull("Invalid field 7 string", s.getFieldAsFloat(7));
            
            Assert.assertNull("Invalid field 8 string", s.getField(8));
            Assert.assertNull("Invalid field 8 string", s.getFieldAsFloat(8));
            
            Assert.assertNull("Invalid field 9 float", s.getFieldAsInteger(9));
            
            Assert.assertNull("Invalid field 10 string", s.getField(10));
            Assert.assertNull("Invalid field 10 string", s.getFieldAsFloat(10));
            
            Assert.assertNull("Invalid field 11 string", s.getField(11));
            
            Assert.assertNull("Invalid field 12 string", s.getField(12));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testParseAPHDG() {
        String rawStr = "$APHDG,257.9,,,13.0,E*08";
        
        try {
            NMEASentence s = new TestNMEASentence(rawStr);
            
            Assert.assertTrue("Should be valid", s.getIsValid());
            
            Assert.assertEquals("Invalid tag", "APHDG", s.getTag());
            Assert.assertEquals("Invalid talked ID", "AP", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "HDG", s.getTypeCode());
            Assert.assertEquals("Invalid checksum", "08", s.getChecksum());
            
            // Validate raw fields
            Assert.assertEquals("Invalid field 0 string", "APHDG", s.getField(0));
            
            Assert.assertEquals("Invalid field 1 string", "257.9", s.getField(1));
            Assert.assertEquals("Invalid field 1 float", new Float(257.9), s.getFieldAsFloat(1));
            
            Assert.assertEquals("Invalid field 2 string", "", s.getField(2));
            
            Assert.assertEquals("Invalid field 3 string", "", s.getField(3));
            
            Assert.assertEquals("Invalid field 4 string", "13.0", s.getField(4));
            Assert.assertEquals("Invalid field 4 float", new Float(13.0), s.getFieldAsFloat(4));
            
            Assert.assertEquals("Invalid field 5 string", "E", s.getField(5));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testParseAPHDG_InvalidChecksum() {
        String rawStr = "$APHDG,257.0,,,13.0,E*08";
        
        try {
            NMEASentence s = new TestNMEASentence(rawStr);
            
            Assert.assertFalse("Should not be valid", s.getIsValid());
            
            Assert.assertEquals("Invalid tag", "APHDG", s.getTag());
            Assert.assertNull("Invalid talked ID", s.getTalkerId());
            Assert.assertNull("Invalid type code", s.getTypeCode());
            Assert.assertEquals("Invalid checksum", "08", s.getChecksum());
            
            // Validate raw fields
            Assert.assertNull("Invalid field 0 string", s.getField(0));
            
            Assert.assertNull("Invalid field 1 string", s.getField(1));
            Assert.assertNull("Invalid field 1 float", s.getFieldAsFloat(1));
            
            Assert.assertNull("Invalid field 2 string", s.getField(2));
            
            Assert.assertNull("Invalid field 3 string", s.getField(3));
            
            Assert.assertNull("Invalid field 4 string", s.getField(4));
            Assert.assertNull("Invalid field 4 float", s.getFieldAsFloat(4));
            
            Assert.assertNull("Invalid field 5 string", s.getField(5));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testParseGPGLL() {
        String rawStr = "$GPGLL,3747.150,N,12216.060,W,205910,A,A*59";
        
        try {
            NMEASentence s = new TestNMEASentence(rawStr);
            
            Assert.assertTrue("Should be valid", s.getIsValid());
            
            Assert.assertEquals("Invalid tag", "GPGLL", s.getTag());
            Assert.assertEquals("Invalid talked ID", "GP", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "GLL", s.getTypeCode());
            Assert.assertEquals("Invalid checksum", "59", s.getChecksum());
            
            // Validate raw fields
            Assert.assertEquals("Invalid field 0 string", "GPGLL", s.getField(0));
            
            Assert.assertEquals("Invalid field 1 string", "3747.150", s.getField(1));
            Assert.assertEquals("Invalid field 1 string", new Float(3747.150), s.getFieldAsFloat(1));
            LatLongTest.validateLatLong("Invalid field 1 LatLong", 37, 47.15F, s.getFieldAsLatLong(1));
            
            Assert.assertEquals("Invalid field 2 string", "N", s.getField(2));
            
            Assert.assertEquals("Invalid field 3 string", "12216.060", s.getField(3));
            Assert.assertEquals("Invalid field 3 string", new Float(12216.060), s.getFieldAsFloat(3));
            LatLongTest.validateLatLong("Invalid field 3 LatLong", 122, 16.06F, s.getFieldAsLatLong(3));
            
            Assert.assertEquals("Invalid field 4 string", "W", s.getField(4));
            
            Assert.assertEquals("Invalid field 5 string", "205910", s.getField(5));
            Assert.assertEquals("Invalid field 5 string", new Integer(205910), s.getFieldAsInteger(5));
            
            Assert.assertEquals("Invalid field 6 string", "A", s.getField(6));
            
            Assert.assertEquals("Invalid field 7 string", "A", s.getField(7));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testParseGPGLL_leadingBang() {
        String rawStr = "!GPGLL,3747.150,N,12216.060,W,205910,A,A*59";
        
        try {
            NMEASentence s = new TestNMEASentence(rawStr);
            
            Assert.assertTrue("Should be valid", s.getIsValid());
            
            Assert.assertEquals("Invalid tag", "GPGLL", s.getTag());
            Assert.assertEquals("Invalid talked ID", "GP", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "GLL", s.getTypeCode());
            Assert.assertEquals("Invalid checksum", "59", s.getChecksum());
            
            // Validate raw fields
            Assert.assertEquals("Invalid field 0 string", "GPGLL", s.getField(0));
            
            Assert.assertEquals("Invalid field 1 string", "3747.150", s.getField(1));
            Assert.assertEquals("Invalid field 1 string", new Float(3747.150), s.getFieldAsFloat(1));
            LatLongTest.validateLatLong("Invalid field 1 LatLong", 37, 47.15F, s.getFieldAsLatLong(1));
            
            Assert.assertEquals("Invalid field 2 string", "N", s.getField(2));
            
            Assert.assertEquals("Invalid field 3 string", "12216.060", s.getField(3));
            Assert.assertEquals("Invalid field 3 string", new Float(12216.060), s.getFieldAsFloat(3));
            LatLongTest.validateLatLong("Invalid field 3 LatLong", 122, 16.06F, s.getFieldAsLatLong(3));
            
            Assert.assertEquals("Invalid field 4 string", "W", s.getField(4));
            
            Assert.assertEquals("Invalid field 5 string", "205910", s.getField(5));
            Assert.assertEquals("Invalid field 5 string", new Integer(205910), s.getFieldAsInteger(5));
            
            Assert.assertEquals("Invalid field 6 string", "A", s.getField(6));
            
            Assert.assertEquals("Invalid field 7 string", "A", s.getField(7));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testParseGPGLL_InvalidChecksum() {
        String rawStr = "$GPGLL,3747.150,N,12216.090,W,205910,A,A*59";
        
        try {
            NMEASentence s = new TestNMEASentence(rawStr);
            
            Assert.assertFalse("Should not be valid", s.getIsValid());
            
            Assert.assertEquals("Invalid tag", "GPGLL", s.getTag());
            Assert.assertNull("Invalid talked ID", s.getTalkerId());
            Assert.assertNull("Invalid type code", s.getTypeCode());
            Assert.assertEquals("Invalid checksum", "59", s.getChecksum());
            
            // Validate raw fields
            Assert.assertNull("Invalid field 0 string", s.getField(0));
            
            Assert.assertNull("Invalid field 1 string", s.getField(1));
            Assert.assertNull("Invalid field 1 string", s.getFieldAsFloat(1));
            Assert.assertNull("Invalid field 1 LatLong", s.getFieldAsLatLong(1));
            
            Assert.assertNull("Invalid field 2 string", s.getField(2));
            
            Assert.assertNull("Invalid field 3 string", s.getField(3));
            Assert.assertNull("Invalid field 3 string", s.getFieldAsFloat(3));
            Assert.assertNull("Invalid field 3 LatLong", s.getFieldAsLatLong(3));
            
            Assert.assertNull("Invalid field 4 string", s.getField(4));
            
            Assert.assertNull("Invalid field 5 string", s.getField(5));
            Assert.assertNull("Invalid field 5 string", s.getFieldAsInteger(5));
            
            Assert.assertNull("Invalid field 6 string", s.getField(6));
            
            Assert.assertNull("Invalid field 7 string", s.getField(7));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }
}
