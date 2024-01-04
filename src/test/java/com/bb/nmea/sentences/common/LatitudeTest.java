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

public class LatitudeTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testCase1_NorthLatitude() {
        String rawLatitude = "3747.151";
        Integer degrees = Integer.valueOf(37);
        Float minutes = Float.valueOf(47.151F);
        
        try {
            Latitude l = new Latitude(rawLatitude);
            
            Assert.assertEquals("Wrong degrees", degrees, l.getDegrees());
            Assert.assertEquals("Wrong minutes", minutes, l.getMinutes());
            Assert.assertEquals("Invalid decimal latitude", Float.valueOf(37.78585F), l.getDecimalLatitude(Direction.NORTH));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testCase1_SouthLatitude() {
        String rawLatitude = "3747.151";
        Integer degrees = Integer.valueOf(37);
        Float minutes = Float.valueOf(47.151F);
        
        try {
            Latitude l = new Latitude(rawLatitude);
            
            Assert.assertEquals("Wrong degrees", degrees, l.getDegrees());
            Assert.assertEquals("Wrong minutes", minutes, l.getMinutes());
            Assert.assertEquals("Invalid decimal latitude", Float.valueOf(-37.78585F), l.getDecimalLatitude(Direction.SOUTH));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testCase2() {
        String rawLatLong = "12223.253";
        Integer degrees = Integer.valueOf(122);
        Float minutes = Float.valueOf(23.253F);
        
        try {
            Latitude l = new Latitude(rawLatLong);
            
            Assert.assertEquals("Wrong degrees", degrees, l.getDegrees());
            Assert.assertEquals("Wrong minutes", minutes, l.getMinutes());
            Assert.assertEquals("Invalid decimal latitude", Float.valueOf(-122.38755F), l.getDecimalLatitude(Direction.SOUTH));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testCase3() {
        String rawLatLong = "16709.033";
        Integer degrees = Integer.valueOf(167);
        Float minutes = Float.valueOf(9.033F);
        
        try {
            Latitude l = new Latitude(rawLatLong);
            
            Assert.assertEquals("Wrong degrees", degrees, l.getDegrees());
            Assert.assertEquals("Wrong minutes", minutes, l.getMinutes());
            Assert.assertEquals("Invalid decimal latitude", Float.valueOf(167.15055F), l.getDecimalLatitude(Direction.NORTH));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testCase4() {
        String rawLatLong = "4533.36";
        Integer degrees = Integer.valueOf(45);
        Float minutes = Float.valueOf(33.36F);
        
        try {
            Latitude l = new Latitude(rawLatLong);
            
            Assert.assertEquals("Wrong degrees", degrees, l.getDegrees());
            Assert.assertEquals("Wrong minutes", minutes, l.getMinutes());
            Assert.assertEquals("Invalid decimal latitude", Float.valueOf(45.556F), l.getDecimalLatitude(Direction.NORTH));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }
    
    @Test
    public void testEastLatitudeFails() {
        String rawValue = "3747.151";
        
        try {
            Latitude l = new Latitude(rawValue);
            
            try {
                l.getDecimalLatitude(Direction.EAST);                
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
    public void testWestLatitudeFails() {
        String rawValue = "3747.151";
        
        try {
            Latitude l = new Latitude(rawValue);
            
            try {
                l.getDecimalLatitude(Direction.WEST);                
                Assert.fail("Should fail");
            } catch (RuntimeException ex) {
                // Ignore expected error
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }
    
    public static void validateLatitude(final Integer degrees, final Float minutes, final Latitude l, final Direction dir) {
        LatLongTest.validateLatLong("Invalid Latitude", degrees, minutes, l);
        
        Float sign = dir.equals(Direction.SOUTH) ? -1.0F : 1.0F;
        Float expDecVal = sign * (degrees + minutes/60.0F);
        Assert.assertEquals("Invalid decimal latitude", expDecVal, l.getDecimalLatitude(dir));
    }
}
