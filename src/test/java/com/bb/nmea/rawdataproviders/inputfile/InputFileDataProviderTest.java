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

import java.io.File;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.bb.nmea.DataProviderException;
import com.bb.nmea.DataProviderTestTools;
import com.bb.nmea.TestTools;
import com.bb.nmea.rawdataproviders.DataCollectorOutputStream;

public class InputFileDataProviderTest {
    private static final String TEST_FILE = "target/test_data/testData.nmea";

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
        TestTools.deleteFile(TEST_FILE);
    }

    @Test
    public void testRead1() {
        String testStr = 
                "$GPVTG,126.0,T,113.0,M,0.1,N,,K,A*0A\n" 
              + "$GPVTG,126.0,T,113.0,M,0.1,N,,K,A*0A\n"
              + "$GPGLL,3746.823,N,12223.246,W,193520,A,A*56\n" 
              + "$APHDG,339.9,,,13.0,E*01" + "$APHDM,339.9,M*33\n"
              + "$APRSA,8.8,A*3E\n" 
              + "$APHDT,352.7,T*30\n"
              + "$GPRMC,193520,A,3746.823,N,12223.246,W,0.1,126.0,291219,013.0,E,A*0C\n"
              + "$GPVTG,126.0,T,113.0,M,0.1,N,,K,A*0A\n" 
              + "$GPVTG,126.0,T,113.0,M,0.1,N,,K,A*0A\n"
              + "$GPGLL,3746.823,N,12223.246,W,193520,A,A*56\n" 
              + "$APHDG,339.7,,,13.0,E*0F\n" 
              + "$APHDM,339.7,M*3D\n"
              + "$APRSA,3.7,A*3A\n" 
              + "$APHDT,352.7,T*30\n"
              + "$GPRMC,193520,A,3746.823,N,12223.246,W,0.1,126.0,291219,013.0,E,A*0C\n";
        
        byte[] testBytes = testStr.getBytes();

        
        try {
            // Write test file
            TestTools.writeFile(TEST_FILE, testStr);
            File testFile = new File(TEST_FILE);
            
            // Create the data provider and process the input
            InputFileDataProvider dp = new InputFileDataProvider(testFile);
            DataCollectorOutputStream oStrm = new DataCollectorOutputStream();
            DataProviderTestTools.setOutputStream(dp, oStrm);

            dp.start();
            
            // Validate
            byte[] collectedBytes = oStrm.getCollectedBytes();
            Assert.assertEquals("Wrong number of collected bytes", testBytes.length, collectedBytes.length);
            
            String collectedStr = new String(collectedBytes);
            Assert.assertEquals("Incorrect string collected", testStr, collectedStr);
            
            for (int n = 0; n < testBytes.length; n++) {
                Assert.assertEquals("Incorrect bytes collected", testBytes[n], collectedBytes[n]);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testRead2() {
        String testStr = 
                "$GPVTG,126.0,T,113.0,M,0.1,N,,K,A*0A\n" 
              + "$GPGLL,3746.823,N,12223.246,W,193520,A,A*56\n" 
              + "$APHDG,339.7,,,13.0,E*0F\n" 
              + "$APRSA,8.8,A*3E\n" 
              + "$APHDT,352.7,T*30\n"
              + "$GPVTG,126.0,T,113.0,M,0.1,N,,K,A*0A\n"
              + "$APHDM,339.7,M*3D\n"
              + "$APRSA,3.7,A*3A\n" 
              + "$GPVTG,126.0,T,113.0,M,0.1,N,,K,A*0A\n"
              + "$GPRMC,193520,A,3746.823,N,12223.246,W,0.1,126.0,291219,013.0,E,A*0C\n"
              + "$GPVTG,126.0,T,113.0,M,0.1,N,,K,A*0A\n" 
              + "$GPGLL,3746.823,N,12223.246,W,193520,A,A*56\n" 
              + "$APHDG,339.9,,,13.0,E*01" + "$APHDM,339.9,M*33\n"
              + "$APHDT,352.7,T*30\n"
              + "$GPRMC,193520,A,3746.823,N,12223.246,W,0.1,126.0,291219,013.0,E,A*0C\n";
        
        byte[] testBytes = testStr.getBytes();

        
        try {
            // Write test file
            TestTools.writeFile(TEST_FILE, testStr);
            File testFile = new File(TEST_FILE);
            
            // Create the data provider and process the input
            InputFileDataProvider dp = new InputFileDataProvider(testFile);
            DataCollectorOutputStream oStrm = new DataCollectorOutputStream();
            DataProviderTestTools.setOutputStream(dp, oStrm);

            dp.start();
            
            // Validate
            byte[] collectedBytes = oStrm.getCollectedBytes();
            Assert.assertEquals("Wrong number of collected bytes", testBytes.length, collectedBytes.length);
            
            String collectedStr = new String(collectedBytes);
            Assert.assertEquals("Incorrect string collected", testStr, collectedStr);
            
            for (int n = 0; n < testBytes.length; n++) {
                Assert.assertEquals("Incorrect bytes collected", testBytes[n], collectedBytes[n]);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testFailsFileDoesntExists() {
        try {
            // Delete test file to insure it does not exist
            TestTools.deleteFile(TEST_FILE);
            File testFile = new File(TEST_FILE);
            
            // Create the data provider and process the input
            InputFileDataProvider dp = new InputFileDataProvider(testFile);
            DataCollectorOutputStream oStrm = new DataCollectorOutputStream();
            DataProviderTestTools.setOutputStream(dp, oStrm);

            try {
                dp.start();
                Assert.fail("Should fail because file does not exist");
            } catch (DataProviderException e) {
                // Ignore expected exception
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testFailsProvidedFileIsDirectory() {
        String testStr = 
                "$GPVTG,126.0,T,113.0,M,0.1,N,,K,A*0A\n" 
              + "$GPGLL,3746.823,N,12223.246,W,193520,A,A*56\n" 
              + "$APHDG,339.7,,,13.0,E*0F\n" 
              + "$APRSA,8.8,A*3E\n" 
              + "$APHDT,352.7,T*30\n"
              + "$GPVTG,126.0,T,113.0,M,0.1,N,,K,A*0A\n"
              + "$APHDM,339.7,M*3D\n"
              + "$APRSA,3.7,A*3A\n" 
              + "$GPVTG,126.0,T,113.0,M,0.1,N,,K,A*0A\n"
              + "$GPRMC,193520,A,3746.823,N,12223.246,W,0.1,126.0,291219,013.0,E,A*0C\n"
              + "$GPVTG,126.0,T,113.0,M,0.1,N,,K,A*0A\n" 
              + "$GPGLL,3746.823,N,12223.246,W,193520,A,A*56\n" 
              + "$APHDG,339.9,,,13.0,E*01" + "$APHDM,339.9,M*33\n"
              + "$APHDT,352.7,T*30\n"
              + "$GPRMC,193520,A,3746.823,N,12223.246,W,0.1,126.0,291219,013.0,E,A*0C\n";

        try {
            // Write test file
            TestTools.writeFile(TEST_FILE, testStr);
            File testFile = new File("/tmp");
            
            // Create the data provider and process the input
            InputFileDataProvider dp = new InputFileDataProvider(testFile);
            DataCollectorOutputStream oStrm = new DataCollectorOutputStream();
            DataProviderTestTools.setOutputStream(dp, oStrm);

            try {
                dp.start();
                Assert.fail("Should fail because file does not exist");
            } catch (DataProviderException e) {
                // Ignore expected exception
            }
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testStopWorks() {
        String testStr = 
                "$GPRMC,193520,A,3746.823,N,12223.246,W,0.1,126.0,291219,013.0,E,AA*0C\n";
        
        try {
            // Write test file (7000 bytes total)
            int totalByteCount = 0;
            for (int n = 0; n < 100; n++) {
                TestTools.appendToFile(TEST_FILE, testStr);
                totalByteCount += 70;
            }
            
            File testFile = new File(TEST_FILE);
            
            // Create the data provider and process the input
            InputFileDataProvider dp = new InputFileDataProvider(testFile);
            DataCollectorOutputStream oStrm = new DataCollectorOutputStream();
            DataProviderTestTools.setOutputStream(dp, oStrm);

            // Start the thread to run the DataProvider
            Thread thr = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        dp.start();
                    } catch (DataProviderException e) {
                        e.printStackTrace();
                        Assert.fail("Unexpected exception in executing thread: " + e.getMessage());
                    }
                }
            });
            thr.start();
            
            // Interrupt the DataProvider
            Thread.sleep(500L);
            dp.stop();
            
            // Validate
            byte[] collectedBytes = oStrm.getCollectedBytes();
            System.out.println("Num Bytes Read: " + collectedBytes.length);
            Assert.assertTrue("Collected all of the data", collectedBytes.length < totalByteCount);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unexpected exception: " + e.getMessage());
        }
    }
}
