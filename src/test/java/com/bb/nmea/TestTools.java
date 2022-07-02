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

import java.io.File;
import java.io.FileWriter;
import java.io.PipedOutputStream;

import org.junit.Assert;

/**
 *
 *
 * @author Scott Stanley
 *
 */
public class TestTools {
    
    public static void writeFile(final String fileName, final String content) {
        File f = new File(fileName);
        
        File testDir = f.getParentFile();
        if (! testDir.exists()) {
            testDir.mkdirs();
        }
        
        FileWriter wr = null;
        try {
            wr = new FileWriter(f);
            wr.write(content);
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail("Failed to write file");
        } finally {
            if (wr != null) 
                try {
                    wr.close();
                } catch (Exception ex) {};
        }
    }
    
    public static void appendToFile(final String fileName, final String content) {
        File f = new File(fileName);
        
        File testDir = f.getParentFile();
        if (! testDir.exists()) {
            testDir.mkdirs();
        }
        
        FileWriter wr = null;
        try {
            wr = new FileWriter(f, true);
            wr.write(content);
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail("Failed to write file");
        } finally {
            if (wr != null) 
                try {
                    wr.close();
                } catch (Exception ex) {};
        }
    }
    
    public static void deleteFile(final String fileName) {
        File f = new File(fileName);
        f.delete();
    }
    
    /**
     * Set the output stream on the provided data provider.
     * 
     * @param dp
     * @param oStrm
     */
    public static void setOutputStream(final DataProvider dp, final PipedOutputStream oStrm) {
        dp.setOutputStream(oStrm);
    }
}
