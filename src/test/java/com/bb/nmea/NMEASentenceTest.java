/*
 * Copyright 2020 Scott Alan Stanley
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bb.nmea;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bb.nmea.sentences.common.LatLongTest;
import com.bb.nmea.sentences.common.UtcTimeTest;

import junit.framework.Assert;

public class NMEASentenceTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testIsValid_InvalidMissing$() {
        String rawStr = "APRSA,8.4,A*32";
        
        try {
            Assert.assertFalse("Message should be invalid", NMEASentence.isValidRawSentence(rawStr));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testIsValid_InvalidMissingChecksum() {
        String rawStr = "$APRSA,8.4,A";
        
        try {
            Assert.assertFalse("Message should be invalid", NMEASentence.isValidRawSentence(rawStr));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testIsValid_InvalidChecksumLength() {
        String rawStr = "$APRSA,8.4,A*3";
        
        try {
            Assert.assertFalse("Message should be invalid", NMEASentence.isValidRawSentence(rawStr));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testParseAPHDT() {
        String rawStr = "$APHDT,357.3,T*31";
        
        try {
            Assert.assertTrue("Message should be valid", NMEASentence.isValidRawSentence(rawStr));

            long preTime = System.currentTimeMillis();
            NMEASentence s = new TestNMEASentence(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertTrue("Sentence should be valid", s.isValid());
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "APHDT", s.getTag());
            Assert.assertEquals("Invalid talked ID", "AP", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "HDT", s.getTypeCode());
            Assert.assertEquals("Invalid checksum", "31", s.getChecksum());
            Assert.assertEquals("Incorrect number of fields", Integer.valueOf(3), s.getNumFields());
            
            // Validate raw fields
            Assert.assertEquals("Invalid field 0 string", "APHDT", s.getField(0));
            
            Assert.assertEquals("Invalid field 1 string", "357.3", s.getField(1));
            Assert.assertEquals("Invalid field 1 float", Float.valueOf(357.3F), s.getFieldAsFloat(1));
            
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
            Assert.assertTrue("Message should be valid", NMEASentence.isValidRawSentence(rawStr));

            long preTime = System.currentTimeMillis();
            NMEASentence s = new TestNMEASentence(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertTrue("Sentence should be valid", s.isValid());
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "APHDT", s.getTag());
            Assert.assertEquals("Invalid talked ID", "AP", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "HDT", s.getTypeCode());
            Assert.assertEquals("Invalid checksum", "31", s.getChecksum());
            Assert.assertEquals("Incorrect number of fields", Integer.valueOf(3), s.getNumFields());
            
            // Validate raw fields
            Assert.assertEquals("Invalid field 0 string", "APHDT", s.getField(0));
            
            Assert.assertEquals("Invalid field 1 string", "357.3", s.getField(1));
            Assert.assertEquals("Invalid field 1 float", Float.valueOf(357.3F), s.getFieldAsFloat(1));
            
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
            Assert.assertFalse("Message should be invalid", NMEASentence.isValidRawSentence(rawStr));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testParseGPRMC() {
        String rawStr = "$GPRMC,065830,A,3746.830,N,12223.251,W,0.0,000.0,291219,013.0,E,A*08";
        
        try {
            Assert.assertTrue("Message should be valid", NMEASentence.isValidRawSentence(rawStr));

            long preTime = System.currentTimeMillis();
            NMEASentence s = new TestNMEASentence(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertTrue("Sentence should be valid", s.isValid());
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "GPRMC", s.getTag());
            Assert.assertEquals("Invalid talked ID", "GP", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "RMC", s.getTypeCode());
            Assert.assertEquals("Invalid checksum", "08", s.getChecksum());
            Assert.assertEquals("Incorrect number of fields", Integer.valueOf(13), s.getNumFields());
            
            // Validate raw fields
            Assert.assertEquals("Invalid field 0 string", "GPRMC", s.getField(0));
            
            Assert.assertEquals("Invalid field 1 string", "065830", s.getField(1));
            Assert.assertEquals("Invalid field 1 float", Integer.valueOf(65830), s.getFieldAsInteger(1));
            
            Assert.assertEquals("Invalid field 2 string", "A", s.getField(2));
            
            Assert.assertEquals("Invalid field 3 string", "3746.830", s.getField(3));
            Assert.assertEquals("Invalid field 3 string", Float.valueOf(3746.830F), s.getFieldAsFloat(3));
            LatLongTest.validateLatLong("Invalid field 3 LatLong", 37, 46.83F, s.getFieldAsLatitude(3));
            
            Assert.assertEquals("Invalid field 4 string", "N", s.getField(4));
            
            Assert.assertEquals("Invalid field 5 string", "12223.251", s.getField(5));
            Assert.assertEquals("Invalid field 5 string", Float.valueOf(12223.251F), s.getFieldAsFloat(5));
            LatLongTest.validateLatLong("Invalid field 5 LatLong", 122, 23.251F, s.getFieldAsLongitude(5));
            
            Assert.assertEquals("Invalid field 6 string", "W", s.getField(6));
            
            Assert.assertEquals("Invalid field 7 string", "0.0", s.getField(7));
            Assert.assertEquals("Invalid field 7 string", Float.valueOf(0.0F), s.getFieldAsFloat(7));
            
            Assert.assertEquals("Invalid field 8 string", "000.0", s.getField(8));
            Assert.assertEquals("Invalid field 8 string", Float.valueOf(000.0F), s.getFieldAsFloat(8));
            
            Assert.assertEquals("Invalid field 9 float", Integer.valueOf(291219), s.getFieldAsInteger(9));
            
            Assert.assertEquals("Invalid field 10 string", "013.0", s.getField(10));
            Assert.assertEquals("Invalid field 10 string", Float.valueOf(013.0F), s.getFieldAsFloat(10));
            
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
            Assert.assertFalse("Message should be invalid", NMEASentence.isValidRawSentence(rawStr));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testParseAPHDG() {
        String rawStr = "$APHDG,257.9,,,13.0,E*08";
        
        try {
            Assert.assertTrue("Message should be valid", NMEASentence.isValidRawSentence(rawStr));

            long preTime = System.currentTimeMillis();
            NMEASentence s = new TestNMEASentence(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertTrue("Sentence should be valid", s.isValid());
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "APHDG", s.getTag());
            Assert.assertEquals("Invalid talked ID", "AP", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "HDG", s.getTypeCode());
            Assert.assertEquals("Invalid checksum", "08", s.getChecksum());
            Assert.assertEquals("Incorrect number of fields", Integer.valueOf(6), s.getNumFields());
            
            // Validate raw fields
            Assert.assertEquals("Invalid field 0 string", "APHDG", s.getField(0));
            
            Assert.assertEquals("Invalid field 1 string", "257.9", s.getField(1));
            Assert.assertEquals("Invalid field 1 float", Float.valueOf(257.9F), s.getFieldAsFloat(1));
            
            Assert.assertEquals("Invalid field 2 string", "", s.getField(2));
            
            Assert.assertEquals("Invalid field 3 string", "", s.getField(3));
            
            Assert.assertEquals("Invalid field 4 string", "13.0", s.getField(4));
            Assert.assertEquals("Invalid field 4 float", Float.valueOf(13.0F), s.getFieldAsFloat(4));
            
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
            Assert.assertFalse("Message should be invalid", NMEASentence.isValidRawSentence(rawStr));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testParseGPGLL() {
        String rawStr = "$GPGLL,3747.150,N,12216.060,W,205910,A,A*59";
        
        try {
            Assert.assertTrue("Message should be valid", NMEASentence.isValidRawSentence(rawStr));

            long preTime = System.currentTimeMillis();
            NMEASentence s = new TestNMEASentence(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertTrue("Sentence should be valid", s.isValid());
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "GPGLL", s.getTag());
            Assert.assertEquals("Invalid talked ID", "GP", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "GLL", s.getTypeCode());
            Assert.assertEquals("Invalid checksum", "59", s.getChecksum());
            Assert.assertEquals("Incorrect number of fields", Integer.valueOf(8), s.getNumFields());
            
            // Validate raw fields
            Assert.assertEquals("Invalid field 0 string", "GPGLL", s.getField(0));
            
            Assert.assertEquals("Invalid field 1 string", "3747.150", s.getField(1));
            Assert.assertEquals("Invalid field 1 string", Float.valueOf(3747.150F), s.getFieldAsFloat(1));
            LatLongTest.validateLatLong("Invalid field 1 LatLong", 37, 47.15F, s.getFieldAsLatitude(1));
            
            Assert.assertEquals("Invalid field 2 string", "N", s.getField(2));
            
            Assert.assertEquals("Invalid field 3 string", "12216.060", s.getField(3));
            Assert.assertEquals("Invalid field 3 string", Float.valueOf(12216.060F), s.getFieldAsFloat(3));
            LatLongTest.validateLatLong("Invalid field 3 LatLong", 122, 16.06F, s.getFieldAsLongitude(3));
            
            Assert.assertEquals("Invalid field 4 string", "W", s.getField(4));
            
            Assert.assertEquals("Invalid field 5 string", "205910", s.getField(5));
            UtcTimeTest.validateUTCTime(20, 59, 10F, s.getFieldAsUTCTime(5));
            Assert.assertEquals("Invalid field 5 string", Integer.valueOf(205910), s.getFieldAsInteger(5));
            
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
            Assert.assertTrue("Message should be valid", NMEASentence.isValidRawSentence(rawStr));

            long preTime = System.currentTimeMillis();
            NMEASentence s = new TestNMEASentence(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertTrue("Sentence should be valid", s.isValid());
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "GPGLL", s.getTag());
            Assert.assertEquals("Invalid talked ID", "GP", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "GLL", s.getTypeCode());
            Assert.assertEquals("Invalid checksum", "59", s.getChecksum());
            Assert.assertEquals("Incorrect number of fields", Integer.valueOf(8), s.getNumFields());
            
            // Validate raw fields
            Assert.assertEquals("Invalid field 0 string", "GPGLL", s.getField(0));
            
            Assert.assertEquals("Invalid field 1 string", "3747.150", s.getField(1));
            Assert.assertEquals("Invalid field 1 string", Float.valueOf(3747.150F), s.getFieldAsFloat(1));
            LatLongTest.validateLatLong("Invalid field 1 LatLong", 37, 47.15F, s.getFieldAsLatitude(1));
            
            Assert.assertEquals("Invalid field 2 string", "N", s.getField(2));
            
            Assert.assertEquals("Invalid field 3 string", "12216.060", s.getField(3));
            Assert.assertEquals("Invalid field 3 string", Float.valueOf(12216.060F), s.getFieldAsFloat(3));
            LatLongTest.validateLatLong("Invalid field 3 LatLong", 122, 16.06F, s.getFieldAsLongitude(3));
            
            Assert.assertEquals("Invalid field 4 string", "W", s.getField(4));
            
            Assert.assertEquals("Invalid field 5 string", "205910", s.getField(5));
            UtcTimeTest.validateUTCTime(20, 59, 10F, s.getFieldAsUTCTime(5));
            Assert.assertEquals("Invalid field 5 string", Integer.valueOf(205910), s.getFieldAsInteger(5));
            
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
            Assert.assertFalse("Message should be invalid", NMEASentence.isValidRawSentence(rawStr));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }
}
