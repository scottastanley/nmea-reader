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

import com.bb.nmea.sentences.common.HeadingType;

import junit.framework.Assert;

public class HDM_Test {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testCase1() {
        String rawStr = "$APHDM,345.5,M*34";
        
        try {
            long preTime = System.currentTimeMillis();
            HDM s = new HDM(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "APHDM", s.getTag());
            Assert.assertEquals("Invalid talked ID", "AP", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "HDM", s.getTypeCode());
            Assert.assertEquals("Invalid checksum", "34", s.getChecksum());
            
            Assert.assertEquals("Invalid heading", Float.valueOf(345.5f), s.getHeadingDegrees());
            Assert.assertEquals("Invalid heading type", HeadingType.MAGNETIC, s.getHeadingType());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testCase2() {
        String rawStr = "$APHDM,344.8,M*38";
        
        try {
            long preTime = System.currentTimeMillis();
            HDM s = new HDM(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "APHDM", s.getTag());
            Assert.assertEquals("Invalid talked ID", "AP", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "HDM", s.getTypeCode());
            Assert.assertEquals("Invalid checksum", "38", s.getChecksum());
            
            Assert.assertEquals("Invalid heading", Float.valueOf(344.8f), s.getHeadingDegrees());
            Assert.assertEquals("Invalid heading type", HeadingType.MAGNETIC, s.getHeadingType());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }
}
