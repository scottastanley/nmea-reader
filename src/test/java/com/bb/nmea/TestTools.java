/* 
 * Copyright 2013 Scott Alan Stanley
 */
package com.bb.nmea;

import java.io.File;
import java.io.FileWriter;

import junit.framework.Assert;

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
}
