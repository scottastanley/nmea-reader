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
package com.bb.nmea.rawdataproviders.inputfile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CountDownLatch;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.bb.nmea.DataProviderException;
import com.bb.nmea.dataproviders.DataCollectorOutputStream;

public class FileReaderTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testDefaultReadSize() {
        String origStr = 
          "$GPVTG,126.0,T,113.0,M,0.1,N,,K,A*0A" 
        + "$GPVTG,126.0,T,113.0,M,0.1,N,,K,A*0A"
        + "$GPGLL,3746.823,N,12223.246,W,193520,A,A*56" 
        + "$APHDG,339.9,,,13.0,E*01" + "$APHDM,339.9,M*33"
        + "$APRSA,8.8,A*3E" 
        + "$APHDT,352.7,T*30"
        + "$GPRMC,193520,A,3746.823,N,12223.246,W,0.1,126.0,291219,013.0,E,A*0C"
        + "$GPVTG,126.0,T,113.0,M,0.1,N,,K,A*0A" 
        + "$GPVTG,126.0,T,113.0,M,0.1,N,,K,A*0A"
        + "$GPGLL,3746.823,N,12223.246,W,193520,A,A*56" 
        + "$APHDG,339.7,,,13.0,E*0F" 
        + "$APHDM,339.7,M*3D"
        + "$APRSA,3.7,A*3A" 
        + "$APHDT,352.7,T*30"
        + "$GPRMC,193520,A,3746.823,N,12223.246,W,0.1,126.0,291219,013.0,E,A*0C";
        byte[] origBytes = origStr.getBytes();

        DataCollectorOutputStream oStrm = new DataCollectorOutputStream();
        try {
            InputStream inpStr = new ByteArrayInputStream(origBytes);
            
            CountDownLatch latch = new CountDownLatch(1);
            
            FileReader rdr = new FileReader((byte[] data, int numBytes) ->  {
                try {
                    oStrm.write(data, 0, numBytes);
                } catch (IOException e) {
                    throw new DataProviderException("", e);
                    }
            }, inpStr, latch, 0L);
            
            rdr.run();
            
            byte[] collectedBytes = oStrm.getCollectedBytes();
            Assert.assertEquals("Wrong number of collected bytes", origBytes.length, collectedBytes.length);
            
            String collectedStr = new String(collectedBytes);
            Assert.assertEquals("Incorrect string collected", origStr, collectedStr);
            
            for (int n = 0; n < origBytes.length; n++) {
                Assert.assertEquals("Incorrect bytes collected", origBytes[n], collectedBytes[n]);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unexpected exception: " + e.getMessage());
        } finally {
            try {
                if (oStrm != null) oStrm.close();
            } catch (IOException e) {}
        }
    }

    @Test
    public void test1ByteReadSize() {
        String origStr = 
          "$GPVTG,126.0,T,113.0,M,0.1,N,,K,A*0A" 
        + "$GPVTG,126.0,T,113.0,M,0.1,N,,K,A*0A"
        + "$GPGLL,3746.823,N,12223.246,W,193520,A,A*56" 
        + "$APHDG,339.9,,,13.0,E*01" + "$APHDM,339.9,M*33"
        + "$APRSA,8.8,A*3E" 
        + "$APHDT,352.7,T*30"
        + "$GPRMC,193520,A,3746.823,N,12223.246,W,0.1,126.0,291219,013.0,E,A*0C"
        + "$GPVTG,126.0,T,113.0,M,0.1,N,,K,A*0A" 
        + "$GPVTG,126.0,T,113.0,M,0.1,N,,K,A*0A"
        + "$GPGLL,3746.823,N,12223.246,W,193520,A,A*56" 
        + "$APHDG,339.7,,,13.0,E*0F" 
        + "$APHDM,339.7,M*3D"
        + "$APRSA,3.7,A*3A" 
        + "$APHDT,352.7,T*30"
        + "$GPRMC,193520,A,3746.823,N,12223.246,W,0.1,126.0,291219,013.0,E,A*0C";
        byte[] origBytes = origStr.getBytes();

        DataCollectorOutputStream oStrm = new DataCollectorOutputStream();
        try {
            InputStream inpStr = new ByteArrayInputStream(origBytes);
            CountDownLatch latch = new CountDownLatch(1);
            
            FileReader rdr = new FileReader((byte[] data, int numBytes) ->  {
                try {
                    oStrm.write(data, 0, numBytes);
                } catch (IOException e) {
                    throw new DataProviderException("", e);
                    }
            }, inpStr, latch, 0L);

            rdr.setMaxBytesPerBlock(1);
            rdr.run();
            
            byte[] collectedBytes = oStrm.getCollectedBytes();
            Assert.assertEquals("Wrong number of collected bytes", origBytes.length, collectedBytes.length);
            
            String collectedStr = new String(collectedBytes);
            Assert.assertEquals("Incorrect string collected", origStr, collectedStr);
            
            for (int n = 0; n < origBytes.length; n++) {
                Assert.assertEquals("Incorrect bytes collected", origBytes[n], collectedBytes[n]);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unexpected exception: " + e.getMessage());
        } finally {
            try {
                if (oStrm != null) oStrm.close();
            } catch (IOException e) {}
        }
    }

    @Test
    public void testAllBytesReadSize() {
        String origStr = 
          "$GPVTG,126.0,T,113.0,M,0.1,N,,K,A*0A" 
        + "$GPVTG,126.0,T,113.0,M,0.1,N,,K,A*0A"
        + "$GPGLL,3746.823,N,12223.246,W,193520,A,A*56" 
        + "$APHDG,339.9,,,13.0,E*01" + "$APHDM,339.9,M*33"
        + "$APRSA,8.8,A*3E" 
        + "$APHDT,352.7,T*30"
        + "$GPRMC,193520,A,3746.823,N,12223.246,W,0.1,126.0,291219,013.0,E,A*0C"
        + "$GPVTG,126.0,T,113.0,M,0.1,N,,K,A*0A" 
        + "$GPVTG,126.0,T,113.0,M,0.1,N,,K,A*0A"
        + "$GPGLL,3746.823,N,12223.246,W,193520,A,A*56" 
        + "$APHDG,339.7,,,13.0,E*0F" 
        + "$APHDM,339.7,M*3D"
        + "$APRSA,3.7,A*3A" 
        + "$APHDT,352.7,T*30"
        + "$GPRMC,193520,A,3746.823,N,12223.246,W,0.1,126.0,291219,013.0,E,A*0C";
        byte[] origBytes = origStr.getBytes();

        DataCollectorOutputStream oStrm = new DataCollectorOutputStream();
        try {
            InputStream inpStr = new ByteArrayInputStream(origBytes);
            CountDownLatch latch = new CountDownLatch(1);
            
            FileReader rdr = new FileReader((byte[] data, int numBytes) ->  {
                try {
                    oStrm.write(data, 0, numBytes);
                } catch (IOException e) {
                    throw new DataProviderException("", e);
                    }
            }, inpStr, latch, 0L);

            rdr.setMaxBytesPerBlock(origBytes.length);
            rdr.run();
            
            byte[] collectedBytes = oStrm.getCollectedBytes();
            Assert.assertEquals("Wrong number of collected bytes", origBytes.length, collectedBytes.length);
            
            String collectedStr = new String(collectedBytes);
            Assert.assertEquals("Incorrect string collected", origStr, collectedStr);
            
            for (int n = 0; n < origBytes.length; n++) {
                Assert.assertEquals("Incorrect bytes collected", origBytes[n], collectedBytes[n]);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unexpected exception: " + e.getMessage());
        } finally {
            try {
                if (oStrm != null) oStrm.close();
            } catch (IOException e) {}
        }
    }

    @Test
    public void testReadSizeGTDataSize() {
        String origStr = 
          "$GPVTG,126.0,T,113.0,M,0.1,N,,K,A*0A" 
        + "$GPVTG,126.0,T,113.0,M,0.1,N,,K,A*0A"
        + "$GPGLL,3746.823,N,12223.246,W,193520,A,A*56" 
        + "$APHDG,339.9,,,13.0,E*01" + "$APHDM,339.9,M*33"
        + "$APRSA,8.8,A*3E" 
        + "$APHDT,352.7,T*30"
        + "$GPRMC,193520,A,3746.823,N,12223.246,W,0.1,126.0,291219,013.0,E,A*0C"
        + "$GPVTG,126.0,T,113.0,M,0.1,N,,K,A*0A" 
        + "$GPVTG,126.0,T,113.0,M,0.1,N,,K,A*0A"
        + "$GPGLL,3746.823,N,12223.246,W,193520,A,A*56" 
        + "$APHDG,339.7,,,13.0,E*0F" 
        + "$APHDM,339.7,M*3D"
        + "$APRSA,3.7,A*3A" 
        + "$APHDT,352.7,T*30"
        + "$GPRMC,193520,A,3746.823,N,12223.246,W,0.1,126.0,291219,013.0,E,A*0C";
        byte[] origBytes = origStr.getBytes();

        DataCollectorOutputStream oStrm = new DataCollectorOutputStream();
        try {
            InputStream inpStr = new ByteArrayInputStream(origBytes);
            CountDownLatch latch = new CountDownLatch(1);
            
            FileReader rdr = new FileReader((byte[] data, int numBytes) ->  {
                try {
                    oStrm.write(data, 0, numBytes);
                } catch (IOException e) {
                    throw new DataProviderException("", e);
                    }
            }, inpStr, latch, 0L);

            rdr.setMaxBytesPerBlock(origBytes.length + 50);
            rdr.run();
            
            byte[] collectedBytes = oStrm.getCollectedBytes();
            Assert.assertEquals("Wrong number of collected bytes", origBytes.length, collectedBytes.length);
            
            String collectedStr = new String(collectedBytes);
            Assert.assertEquals("Incorrect string collected", origStr, collectedStr);
            
            for (int n = 0; n < origBytes.length; n++) {
                Assert.assertEquals("Incorrect bytes collected", origBytes[n], collectedBytes[n]);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unexpected exception: " + e.getMessage());
        } finally {
            try {
                if (oStrm != null) oStrm.close();
            } catch (IOException e) {}
        }
    }

    @Test
    public void testReadSizeEvenMultipleOfSize() {
        String origStr = 
          "$GPVTG,126.0,T,113.0,M,0.1,N,,K,A*0A" 
        + "$GPVTG,126.0,T,113.0,M,0.1,N,,K,A*0A"
        + "$GPGLL,3746.823,N,12223.246,W,193520,A,A*56" 
        + "$APHDG,339.9,,,13.0,E*01" + "$APHDM,339.9,M*33"
        + "$APRSA,8.8,A*3E" 
        + "$APHDT,352.7,T*30"
        + "$GPRMC,193520,A,3746.823,N,12223.246,W,0.1,126.0,291219,013.0,E,A*0C"
        + "$GPVTG,126.0,T,113.0,M,0.1,N,,K,A*0A" 
        + "$GPVTG,126.0,T,113.0,M,0.1,N,,K,A*0A"
        + "$GPGLL,3746.823,N,12223.246,W,193520,A,A*56" 
        + "$APHDG,339.7,,,13.0,E*0F" 
        + "$APHDM,339.7,M*3D"
        + "$APRSA,3.7,A*3A" 
        + "$APHDT,352.7,T*30"
        + "$GPRMC,193520,A,3746.823,N,12223.246,W,0.1,126.0,291219,013.0,E,A*0C";
        byte[] origBytes = origStr.getBytes();

        DataCollectorOutputStream oStrm = new DataCollectorOutputStream();
        try {
            InputStream inpStr = new ByteArrayInputStream(origBytes);
            CountDownLatch latch = new CountDownLatch(1);
            
            FileReader rdr = new FileReader((byte[] data, int numBytes) ->  {
                try {
                    oStrm.write(data, 0, numBytes);
                } catch (IOException e) {
                    throw new DataProviderException("", e);
                    }
            }, inpStr, latch, 0L);

            rdr.setMaxBytesPerBlock(origBytes.length/4);
            rdr.run();
            
            byte[] collectedBytes = oStrm.getCollectedBytes();
            Assert.assertEquals("Wrong number of collected bytes", origBytes.length, collectedBytes.length);
            
            String collectedStr = new String(collectedBytes);
            Assert.assertEquals("Incorrect string collected", origStr, collectedStr);
            
            for (int n = 0; n < origBytes.length; n++) {
                Assert.assertEquals("Incorrect bytes collected", origBytes[n], collectedBytes[n]);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unexpected exception: " + e.getMessage());
        } finally {
            try {
                if (oStrm != null) oStrm.close();
            } catch (IOException e) {}
        }
    }
}
