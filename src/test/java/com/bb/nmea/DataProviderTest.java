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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doThrow;

import java.io.IOException;
import java.io.PipedOutputStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bb.nmea.dataproviders.TestPassThroughDataProvider;

import junit.framework.Assert;

public class DataProviderTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSingleCall() {
        byte[][] origBytes = {
                    "$GPVTG,118.0,T,105.0,M,0.1,N,,K,A*00".getBytes()
                };
        try {
            TestPassThroughDataProvider dp = new TestPassThroughDataProvider(origBytes);
            
            PipedOutputStream oStrm = mock(PipedOutputStream.class);
            
            ((DataProvider) dp).setOutputStream(oStrm);
            dp.start();
            
            for (byte[] b : origBytes) {
                verify(oStrm, times(1)).write(b, 0, b.length);
            }                        
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testMultipleCalls() {
        byte[][] origBytes = {
                    "$GPVTG,118.0,T,105.0,M,0.1,N,,K,A*00".getBytes(),
                    "$GPRMC,065830,A,3746.830,N,12223.251,W,0.0,000.0,291219,013.0,E,A*08".getBytes(),
                    "$APHDM,344.3,M*33".getBytes(),
                    "$GPVTG,000.0,T,347.0,M,0.0,N,,K,A*0D".getBytes(),
                    "$APHDG,345.5,,,13.0,E*06".getBytes(),
                };
        try {
            TestPassThroughDataProvider dp = new TestPassThroughDataProvider(origBytes);
            
            PipedOutputStream oStrm = mock(PipedOutputStream.class);
            
            ((DataProvider) dp).setOutputStream(oStrm);
            dp.start();
            
            for (byte[] b : origBytes) {
                verify(oStrm, times(1)).write(b, 0, b.length);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }
    
    @Test
    public void testThrowsRuntimeExceptionIfNoOStrmCfg() {
        byte[][] origBytes = {
                    "$GPVTG,118.0,T,105.0,M,0.1,N,,K,A*00".getBytes()
                };
        try {
            TestPassThroughDataProvider dp = new TestPassThroughDataProvider(origBytes);
            
            try {
                dp.start();
                
                Assert.fail("Should throw an exception");
            } catch (RuntimeException e) {
                // Ignore expected exception
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }
    

    @Test
    public void testThrowsExceptionWhenWriteFails() {
        byte[][] origBytes = {
                    "$GPVTG,118.0,T,105.0,M,0.1,N,,K,A*00".getBytes()
                };
        try {
            TestPassThroughDataProvider dp = new TestPassThroughDataProvider(origBytes);
            
            PipedOutputStream oStrm = mock(PipedOutputStream.class);
            doThrow(IOException.class).when(oStrm).write(origBytes[0], 0, origBytes[0].length);
            
            ((DataProvider) dp).setOutputStream(oStrm);
            
            try {
                dp.start();
                
                Assert.fail("Should throw an exception");
            } catch (DataProviderException e) {
                // Ignore expected exception
            }
            
            verify(oStrm, times(1)).write(origBytes[0], 0, origBytes[0].length);
                        
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

}
