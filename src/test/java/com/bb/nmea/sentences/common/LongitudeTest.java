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
package com.bb.nmea.sentences.common;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class LongitudeTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testCase1() {
        String raw = "3746.830";
        try {
            Longitude l = new Longitude(raw);
            
            Assert.assertEquals("Incorrect degrees", Integer.valueOf(37), l.getDegrees());
            Assert.assertEquals("Incorrect minutes", Float.valueOf(46.830F), l.getMinutes());
            Assert.assertEquals("Invalid decimal longitude", Float.valueOf(37.7805F), l.getDecimalLongitude(Direction.EAST));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testCase2() {
        String raw = "3747.556";
        try {
            Longitude l = new Longitude(raw);
            
            Assert.assertEquals("Incorrect degrees", Integer.valueOf(37), l.getDegrees());
            Assert.assertEquals("Incorrect minutes", Float.valueOf(47.556F), l.getMinutes());
            Assert.assertEquals("Invalid decimal longitude", Float.valueOf(-37.7926F), l.getDecimalLongitude(Direction.WEST));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testCase3() {
        String raw = "12216.764";
        try {
            Longitude l = new Longitude(raw);
            
            Assert.assertEquals("Incorrect degrees", Integer.valueOf(122), l.getDegrees());
            Assert.assertEquals("Incorrect minutes", Float.valueOf(16.764F), l.getMinutes());
            Assert.assertEquals("Invalid decimal longitude", Float.valueOf(122.2794F), l.getDecimalLongitude(Direction.EAST));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testCase4() {
        String raw = "17916.057";
        try {
            Longitude l = new Longitude(raw);
            
            Assert.assertEquals("Incorrect degrees", Integer.valueOf(179), l.getDegrees());
            Assert.assertEquals("Incorrect minutes", Float.valueOf(16.057F), l.getMinutes());
            Assert.assertEquals("Invalid decimal longitude", Float.valueOf(-179.26762F), l.getDecimalLongitude(Direction.WEST));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testEastLongitude() {
        String rawValue = "12220.10";
        Integer degrees = Integer.valueOf(122);
        Float minutes = Float.valueOf(20.10F);
        
        try {
            Longitude l = new Longitude(rawValue);
            
            Assert.assertEquals("Wrong degrees", degrees, l.getDegrees());
            Assert.assertEquals("Wrong minutes", minutes, l.getMinutes());
            Assert.assertEquals("Invalid decimal longitude", Float.valueOf(122.335F), l.getDecimalLongitude(Direction.EAST));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testWestLongitude() {
        String rawValue = "12220.10";
        Integer degrees = Integer.valueOf(122);
        Float minutes = Float.valueOf(20.10F);
        
        try {
            Longitude l = new Longitude(rawValue);
            
            Assert.assertEquals("Wrong degrees", degrees, l.getDegrees());
            Assert.assertEquals("Wrong minutes", minutes, l.getMinutes());
            Assert.assertEquals("Invalid decimal longitude", Float.valueOf(-122.335F), l.getDecimalLongitude(Direction.WEST));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testNorthLongitudeFails() {
        String rawValue = "12220.10";
        
        try {
            Longitude l = new Longitude(rawValue);
            
            try {
                l.getDecimalLongitude(Direction.NORTH);                
                Assert.fail("Should fail");
            } catch (RuntimeException ex) {
                // Ignore expected error
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testSouthLongitudeFails() {
        String rawValue = "12220.10";
        
        try {
            Longitude l = new Longitude(rawValue);
            
            try {
                l.getDecimalLongitude(Direction.SOUTH);                
                Assert.fail("Should fail");
            } catch (RuntimeException ex) {
                // Ignore expected error
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }
    
    public static void validateLongitude(final Integer degrees, final Float minutes, final Longitude l, final Direction dir) {
        LatLongTest.validateLatLong("Invalid Longitude", degrees, minutes, l);
        
        Float sign = dir.equals(Direction.WEST) ? -1.0F : 1.0F;
        Float expDecVal = sign * (degrees + minutes/60.0F);
        Assert.assertEquals("Invalid decimal longitude", expDecVal, l.getDecimalLongitude(dir));
    }
}
