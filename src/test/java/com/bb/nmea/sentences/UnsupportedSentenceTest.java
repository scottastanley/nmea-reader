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
package com.bb.nmea.sentences;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;

public class UnsupportedSentenceTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testParseAPHDTasUnsupported() {
        String rawStr = "$APZZZ,357.3,T*31";
        
        try {
            long preTime = System.currentTimeMillis();
            UnsupportedSentence s = new UnsupportedSentence(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertTrue("Sentence should be valid", s.isValid());
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "APZZZ", s.getTag());
            Assert.assertEquals("Invalid type code", "UNSUPPORTED TAG: APZZZ", s.getTypeCode());
            Assert.assertEquals("Invalid checksum", "31", s.getChecksum());

            // Validate raw fields
            Assert.assertEquals("Invalid field 0 string", "APZZZ", s.getUnknownField(0));
            Assert.assertEquals("Invalid field 1 string", "357.3", s.getUnknownField(1));
            Assert.assertEquals("Invalid field 2 string", "T", s.getUnknownField(2));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testParseGPRMCasUnsupported() {
        String rawStr = "$GPZZZ,065830,A,3746.830,N,12223.251,W,0.0,000.0,291219,013.0,E,A*08";
        
        try {
            long preTime = System.currentTimeMillis();
            UnsupportedSentence s = new UnsupportedSentence(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertTrue("Sentence should be valid", s.isValid());
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "GPZZZ", s.getTag());
            Assert.assertEquals("Invalid type code", "UNSUPPORTED TAG: GPZZZ", s.getTypeCode());
            Assert.assertEquals("Invalid checksum", "08", s.getChecksum());
            
            // Validate raw fields
            Assert.assertEquals("Invalid field 0 string", "GPZZZ", s.getUnknownField(0));
            Assert.assertEquals("Invalid field 1 string", "065830", s.getUnknownField(1));            
            Assert.assertEquals("Invalid field 2 string", "A", s.getUnknownField(2));
            Assert.assertEquals("Invalid field 3 string", "3746.830", s.getUnknownField(3));
            Assert.assertEquals("Invalid field 4 string", "N", s.getUnknownField(4));
            Assert.assertEquals("Invalid field 5 string", "12223.251", s.getUnknownField(5));
            Assert.assertEquals("Invalid field 6 string", "W", s.getUnknownField(6));
            Assert.assertEquals("Invalid field 7 string", "0.0", s.getUnknownField(7));
            Assert.assertEquals("Invalid field 8 string", "000.0", s.getUnknownField(8));
            Assert.assertEquals("Invalid field 9 float", "291219", s.getUnknownField(9));
            Assert.assertEquals("Invalid field 10 string", "013.0", s.getUnknownField(10));
            Assert.assertEquals("Invalid field 11 string", "E", s.getUnknownField(11));
            Assert.assertEquals("Invalid field 12 string", "A", s.getUnknownField(12));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }
}
