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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.bb.nmea.sentences.common.Status;

public class RSA_Test {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSingleRudder_StarboardTurn() {
        String rawStr = "$APRSA,8.6,A*30";
        
        try {
            long preTime = System.currentTimeMillis();
            RSA s = new RSA(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "APRSA", s.getTag());
            Assert.assertEquals("Invalid talked ID", "AP", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "RSA", s.getTypeCode());
            Assert.assertEquals("Invalid checksum", "30", s.getChecksum());
            
            Assert.assertEquals("Invalid dual rudder", Boolean.FALSE, s.isDualRudder());
            Assert.assertEquals("Invalid rudder angle, single rudder", Float.valueOf(8.6f), s.getRudderAngle());
            Assert.assertEquals("Invalid rudder status, single rudder", Status.VALID, s.getRudderStatus());
            Assert.assertEquals("Invalid rudder angle, starboard rudder", Float.valueOf(8.6f), s.getStarboardRudderAngle());
            Assert.assertEquals("Invalid rudder status, starboard rudder", Status.VALID, s.getStarboardRudderStatus());
            Assert.assertEquals("Invalid rudder angle, port rudder", null, s.getPortRudderAngle());
            Assert.assertEquals("Invalid rudder status, port rudder", null, s.getPortRudderStatus());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testSingleRudder_PortTurn() {
        String rawStr = "$APRSA,-11.2,A*21";
        
        try {
            long preTime = System.currentTimeMillis();
            RSA s = new RSA(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "APRSA", s.getTag());
            Assert.assertEquals("Invalid talked ID", "AP", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "RSA", s.getTypeCode());
            Assert.assertEquals("Invalid checksum", "21", s.getChecksum());
            
            Assert.assertEquals("Invalid dual rudder", Boolean.FALSE, s.isDualRudder());
            Assert.assertEquals("Invalid rudder angle, single rudder", Float.valueOf(-11.2f), s.getRudderAngle());
            Assert.assertEquals("Invalid rudder status, single rudder", Status.VALID, s.getRudderStatus());
            Assert.assertEquals("Invalid rudder angle, starboard rudder", Float.valueOf(-11.2f), s.getStarboardRudderAngle());
            Assert.assertEquals("Invalid rudder status, starboard rudder", Status.VALID, s.getStarboardRudderStatus());
            Assert.assertEquals("Invalid rudder angle, port rudder", null, s.getPortRudderAngle());
            Assert.assertEquals("Invalid rudder status, port rudder", null, s.getPortRudderStatus());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testDualRudder_StarboardTurn() {
        String rawStr = "$APRSA,8.6,A,8.2,A*30";
        
        try {
            long preTime = System.currentTimeMillis();
            RSA s = new RSA(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "APRSA", s.getTag());
            Assert.assertEquals("Invalid talked ID", "AP", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "RSA", s.getTypeCode());
            Assert.assertEquals("Invalid checksum", "30", s.getChecksum());
            
            Assert.assertEquals("Invalid dual rudder", Boolean.TRUE, s.isDualRudder());
            Assert.assertEquals("Invalid rudder angle, single rudder", Float.valueOf(8.6f), s.getRudderAngle());
            Assert.assertEquals("Invalid rudder status, single rudder", Status.VALID, s.getRudderStatus());
            Assert.assertEquals("Invalid rudder angle, starboard rudder", Float.valueOf(8.6f), s.getStarboardRudderAngle());
            Assert.assertEquals("Invalid rudder status, starboard rudder", Status.VALID, s.getStarboardRudderStatus());
            Assert.assertEquals("Invalid rudder angle, port rudder", Float.valueOf(8.2f), s.getPortRudderAngle());
            Assert.assertEquals("Invalid rudder status, port rudder", Status.VALID, s.getPortRudderStatus());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testDualRudder_PortTurn() {
        String rawStr = "$APRSA,-11.2,A,-10.9,A*21";
        
        try {
            long preTime = System.currentTimeMillis();
            RSA s = new RSA(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "APRSA", s.getTag());
            Assert.assertEquals("Invalid talked ID", "AP", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "RSA", s.getTypeCode());
            Assert.assertEquals("Invalid checksum", "21", s.getChecksum());
            
            Assert.assertEquals("Invalid dual rudder", Boolean.TRUE, s.isDualRudder());
            Assert.assertEquals("Invalid rudder angle, single rudder", Float.valueOf(-11.2f), s.getRudderAngle());
            Assert.assertEquals("Invalid rudder status, single rudder", Status.VALID, s.getRudderStatus());
            Assert.assertEquals("Invalid rudder angle, starboard rudder", Float.valueOf(-11.2f), s.getStarboardRudderAngle());
            Assert.assertEquals("Invalid rudder status, starboard rudder", Status.VALID, s.getStarboardRudderStatus());
            Assert.assertEquals("Invalid rudder angle, port rudder", Float.valueOf(-10.9f), s.getPortRudderAngle());
            Assert.assertEquals("Invalid rudder status, port rudder", Status.VALID, s.getPortRudderStatus());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testDualRudder_InvalidStatus() {
        String rawStr = "$APRSA,8.6,V,8.2,V*30";
        
        try {
            long preTime = System.currentTimeMillis();
            RSA s = new RSA(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "APRSA", s.getTag());
            Assert.assertEquals("Invalid talked ID", "AP", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "RSA", s.getTypeCode());
            Assert.assertEquals("Invalid checksum", "30", s.getChecksum());
            
            Assert.assertEquals("Invalid dual rudder", Boolean.TRUE, s.isDualRudder());
            Assert.assertEquals("Invalid rudder angle, single rudder", Float.valueOf(8.6f), s.getRudderAngle());
            Assert.assertEquals("Invalid rudder status, single rudder", Status.INVALID, s.getRudderStatus());
            Assert.assertEquals("Invalid rudder angle, starboard rudder", Float.valueOf(8.6f), s.getStarboardRudderAngle());
            Assert.assertEquals("Invalid rudder status, starboard rudder", Status.INVALID, s.getStarboardRudderStatus());
            Assert.assertEquals("Invalid rudder angle, port rudder", Float.valueOf(8.2f), s.getPortRudderAngle());
            Assert.assertEquals("Invalid rudder status, port rudder", Status.INVALID, s.getPortRudderStatus());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }
}
