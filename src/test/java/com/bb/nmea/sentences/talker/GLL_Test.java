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
package com.bb.nmea.sentences.talker;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.bb.nmea.sentences.common.Direction;
import com.bb.nmea.sentences.common.FAAModeIndicator;
import com.bb.nmea.sentences.common.LatitudeTest;
import com.bb.nmea.sentences.common.LongitudeTest;
import com.bb.nmea.sentences.common.Status;
import com.bb.nmea.sentences.common.UtcTimeTest;

public class GLL_Test {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testCase2_post_v2_3() {
        String rawStr = "$GPGLL,3513.123,S,02312.453,E,131130.21,A,A*53";
        
        try {
            long preTime = System.currentTimeMillis();
            GLL s = new GLL(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "GPGLL", s.getTag());
            Assert.assertEquals("Invalid talked ID", "GP", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "GLL", s.getSentenceId());
            Assert.assertEquals("Invalid checksum", "53", s.getChecksum());
            
            LatitudeTest.validateLatitude(35, 13.123F, s.getLatitude(), s.getLatitudeDir());
            Assert.assertEquals("Wrong latitude direction", Direction.SOUTH, s.getLatitudeDir());
            
            LongitudeTest.validateLongitude(23, 12.453F, s.getLongitude(), s.getLongitudeDir());
            Assert.assertEquals("Wrong longitude direction", Direction.EAST, s.getLongitudeDir());
            
            UtcTimeTest.validateUTCTime(13,  11,  30.21F,  s.getUtcTime());
            Assert.assertEquals("Incorrect status", Status.VALID, s.getStatus());
            Assert.assertEquals("Incorrect FAA mode", FAAModeIndicator.AUTONOMOUS_MODE, s.getFaaModeIndicator());            
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testCase1_post_v2_3() {
        String rawStr = "$GPGLL,3747.741,N,12220.083,W,190137.52,A,D*79";
        
        try {
            long preTime = System.currentTimeMillis();
            GLL s = new GLL(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "GPGLL", s.getTag());
            Assert.assertEquals("Invalid talked ID", "GP", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "GLL", s.getSentenceId());
            Assert.assertEquals("Invalid checksum", "79", s.getChecksum());
            
            LatitudeTest.validateLatitude(37, 47.741F, s.getLatitude(), s.getLatitudeDir());
            Assert.assertEquals("Wrong latitude direction", Direction.NORTH, s.getLatitudeDir());
            
            LongitudeTest.validateLongitude(122, 20.083F, s.getLongitude(), s.getLongitudeDir());
            Assert.assertEquals("Wrong longitude direction", Direction.WEST, s.getLongitudeDir());
            
            UtcTimeTest.validateUTCTime(19,  01,  37.52F,  s.getUtcTime());
            Assert.assertEquals("Incorrect status", Status.VALID, s.getStatus());
            Assert.assertEquals("Incorrect FAA mode", FAAModeIndicator.DIFFERENTIAL_MODE, s.getFaaModeIndicator());            
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testCase3_pre_v2_3() {
        String rawStr = "$APGLL,3806.9698,N,12137.4570,W,045959.00,A*17";
        
        try {
            long preTime = System.currentTimeMillis();
            GLL s = new GLL(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "APGLL", s.getTag());
            Assert.assertEquals("Invalid talked ID", "AP", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "GLL", s.getSentenceId());
            Assert.assertEquals("Invalid checksum", "17", s.getChecksum());
            
            LatitudeTest.validateLatitude(38, 06.9698F, s.getLatitude(), s.getLatitudeDir());
            Assert.assertEquals("Wrong latitude direction", Direction.NORTH, s.getLatitudeDir());
            
            LongitudeTest.validateLongitude(121, 37.4570F, s.getLongitude(), s.getLongitudeDir());
            Assert.assertEquals("Wrong longitude direction", Direction.WEST, s.getLongitudeDir());
            
            UtcTimeTest.validateUTCTime(04,  59,  59.00F,  s.getUtcTime());
            Assert.assertEquals("Incorrect status", Status.VALID, s.getStatus());
            Assert.assertNull("Incorrect FAA mode", s.getFaaModeIndicator());            
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }
}
