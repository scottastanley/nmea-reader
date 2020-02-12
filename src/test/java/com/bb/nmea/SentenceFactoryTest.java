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

import com.bb.nmea.sentences.HDM;
import com.bb.nmea.sentences.InvalidSentence;
import com.bb.nmea.sentences.UnsupportedSentence;
import com.bb.nmea.sentences.common.HeadingType;

import junit.framework.Assert;

public class SentenceFactoryTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testHDM() {
        String rawStr = "$APHDM,344.4,M*34";

        try {
            SentenceFactory fact = new SentenceFactory();
            NMEASentence s = fact.getNMEASentence(rawStr);
            
            Assert.assertEquals("Incorrect class", HDM.class, s.getClass());
            HDM hdmS = HDM.class.cast(s);
            Assert.assertTrue("Message should be valid", s.isValid());
            Assert.assertEquals("Incorrect raw sentence", rawStr, hdmS.getRawSentence());
            Assert.assertEquals("Incorrect tag", "APHDM", hdmS.getTag());
            Assert.assertEquals("Incorrect tag", "AP", hdmS.getTalkerId());
            Assert.assertEquals("Incorrect tag", "HDM", hdmS.getTypeCode());
            Assert.assertEquals("Incorrect tag", "34", hdmS.getChecksum());
            Assert.assertEquals("Incorrect heading", 344.4F, hdmS.getHeadingDegrees());
            Assert.assertEquals("Incorrect heading type", HeadingType.MAGNETIC, hdmS.getHeadingType());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }
    
    @Test
    public void testAPHDT_InvalidChecksum() {
        String rawStr = "$APHDT,357.9,T*31";
        
        try {
            SentenceFactory fact = new SentenceFactory();
            NMEASentence s = fact.getNMEASentence(rawStr);
            
            Assert.assertEquals("Incorrect class", InvalidSentence.class, s.getClass());
            Assert.assertFalse("Message should be invalid", s.isValid());
            Assert.assertEquals("Incorrect raw sentence", rawStr, s.getRawSentence());
            Assert.assertEquals("Incorrect tag", null, s.getTag());
            Assert.assertEquals("Incorrect talker ID", null, s.getTalkerId());
            Assert.assertEquals("Incorrect type", null, s.getTypeCode());
            Assert.assertEquals("Incorrect checksum", null, s.getChecksum());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testParseAPHDG_InvalidChecksum() {
        String rawStr = "$APHDG,257.0,,,13.0,E*08";
        
        try {
            SentenceFactory fact = new SentenceFactory();
            NMEASentence s = fact.getNMEASentence(rawStr);
            
            Assert.assertEquals("Incorrect class", InvalidSentence.class, s.getClass());
            Assert.assertFalse("Message should be invalid", s.isValid());
            Assert.assertEquals("Incorrect raw sentence", rawStr, s.getRawSentence());
            Assert.assertEquals("Incorrect tag", null, s.getTag());
            Assert.assertEquals("Incorrect talker ID", null, s.getTalkerId());
            Assert.assertEquals("Incorrect type", null, s.getTypeCode());
            Assert.assertEquals("Incorrect checksum", null, s.getChecksum());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testParseAPHDTasUnsupported() {
        String rawStr = "$APZZZ,357.3,T*33";
        
        try {
            SentenceFactory fact = new SentenceFactory();
            
            long preTime = System.currentTimeMillis();
            NMEASentence s = fact.getNMEASentence(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect class", UnsupportedSentence.class, s.getClass());
            UnsupportedSentence sUnsp = (UnsupportedSentence) s;
            Assert.assertTrue("Sentence should be valid", s.isValid());
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "APZZZ", s.getTag());
            Assert.assertEquals("Invalid talked ID", "AP", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "ZZZ", s.getTypeCode());
            Assert.assertEquals("Invalid checksum", "33", s.getChecksum());

            // Validate raw fields
            Assert.assertEquals("Invalid field 0 string", "APZZZ", sUnsp.getUnknownField(0));
            Assert.assertEquals("Invalid field 1 string", "357.3", sUnsp.getUnknownField(1));
            Assert.assertEquals("Invalid field 2 string", "T", sUnsp.getUnknownField(2));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testParseGPRMCasUnsupported() {
        String rawStr = "$GPZZZ,065830,A,3746.830,N,12223.251,W,0.0,000.0,291219,013.0,E,A*0E";
        
        try {
            SentenceFactory fact = new SentenceFactory();
            
            long preTime = System.currentTimeMillis();
            NMEASentence s = fact.getNMEASentence(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect class", UnsupportedSentence.class, s.getClass());
            UnsupportedSentence sUnsp = (UnsupportedSentence) s;
            Assert.assertTrue("Sentence should be valid", s.isValid());
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "GPZZZ", s.getTag());
            Assert.assertEquals("Invalid talked ID", "GP", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "ZZZ", s.getTypeCode());
            Assert.assertEquals("Invalid checksum", "0E", s.getChecksum());
            
            // Validate raw fields
            Assert.assertEquals("Invalid field 0 string", "GPZZZ", sUnsp.getUnknownField(0));
            Assert.assertEquals("Invalid field 1 string", "065830", sUnsp.getUnknownField(1));            
            Assert.assertEquals("Invalid field 2 string", "A", sUnsp.getUnknownField(2));
            Assert.assertEquals("Invalid field 3 string", "3746.830", sUnsp.getUnknownField(3));
            Assert.assertEquals("Invalid field 4 string", "N", sUnsp.getUnknownField(4));
            Assert.assertEquals("Invalid field 5 string", "12223.251", sUnsp.getUnknownField(5));
            Assert.assertEquals("Invalid field 6 string", "W", sUnsp.getUnknownField(6));
            Assert.assertEquals("Invalid field 7 string", "0.0", sUnsp.getUnknownField(7));
            Assert.assertEquals("Invalid field 8 string", "000.0", sUnsp.getUnknownField(8));
            Assert.assertEquals("Invalid field 9 float", "291219", sUnsp.getUnknownField(9));
            Assert.assertEquals("Invalid field 10 string", "013.0", sUnsp.getUnknownField(10));
            Assert.assertEquals("Invalid field 11 string", "E", sUnsp.getUnknownField(11));
            Assert.assertEquals("Invalid field 12 string", "A", sUnsp.getUnknownField(12));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }
}
