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

public class UtcTimeTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testCase1() {
        String rawLatitude = "190137.52";
        Integer hours = Integer.valueOf(19);
        Integer minutes = Integer.valueOf(01);
        Float seconds = Float.valueOf(37.52F);
        
        try {
            UTCTime l = new UTCTime(rawLatitude);
            
            Assert.assertEquals("Wrong hours", hours, l.getHour());
            Assert.assertEquals("Wrong minutes", minutes, l.getMinutes());
            Assert.assertEquals("Wrong seconds", seconds, l.getSeconds());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testCase2() {
        String rawLatitude = "190130";
        Integer hours = Integer.valueOf(19);
        Integer minutes = Integer.valueOf(01);
        Float seconds = Float.valueOf(30F);
        
        try {
            UTCTime l = new UTCTime(rawLatitude);
            
            Assert.assertEquals("Wrong hours", hours, l.getHour());
            Assert.assertEquals("Wrong minutes", minutes, l.getMinutes());
            Assert.assertEquals("Wrong seconds", seconds, l.getSeconds());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testCase3() {
        String rawLatitude = "212340";
        Integer hours = Integer.valueOf(21);
        Integer minutes = Integer.valueOf(23);
        Float seconds = Float.valueOf(40F);
        
        try {
            UTCTime l = new UTCTime(rawLatitude);
            
            Assert.assertEquals("Wrong hours", hours, l.getHour());
            Assert.assertEquals("Wrong minutes", minutes, l.getMinutes());
            Assert.assertEquals("Wrong seconds", seconds, l.getSeconds());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testCase4() {
        String rawLatitude = "212340.18";
        Integer hours = Integer.valueOf(21);
        Integer minutes = Integer.valueOf(23);
        Float seconds = Float.valueOf(40.18F);
        
        try {
            UTCTime l = new UTCTime(rawLatitude);
            
            Assert.assertEquals("Wrong hours", hours, l.getHour());
            Assert.assertEquals("Wrong minutes", minutes, l.getMinutes());
            Assert.assertEquals("Wrong seconds", seconds, l.getSeconds());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }
    
    
    public static void validateUTCTime(final Integer hours, final Integer minutes, final Float seconds, final UTCTime l) {
        Assert.assertEquals("Wrong UTC hours", hours, l.getHour());
        Assert.assertEquals("Wrong UTC minutes", minutes, l.getMinutes());
        Assert.assertEquals("Wrong UTC seconds", seconds, l.getSeconds());
    }
}
