package com.bb.nmea.main;

import java.util.Properties;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FileConfigTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
    static String getFilePropName(final String id, final String propName) {
        String result = FileConfig.FILE_PREFIX;
        if (id != null) {
            result = result + id + ".";
        }
        return result + propName;
    }

    @Test
    public void testGetIdsSingle() {
        Properties props = new Properties();
        props.setProperty(getFilePropName(null, FileConfig.IDS_PROPERTY), "f1");
        
        try {
            String[] ids = FileConfig.getFileIds(props);
            
            Assert.assertEquals("Wrong number if IDs", 1, ids.length);
            Assert.assertEquals("Wrong ID 1", "f1", ids[0]);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testGetIdsMulti() {
        Properties props = new Properties();
        props.setProperty(getFilePropName(null, FileConfig.IDS_PROPERTY), "f1,f2,f3");
        
        try {
            String[] ids = FileConfig.getFileIds(props);
            
            Assert.assertEquals("Wrong number if IDs", 3, ids.length);
            Assert.assertEquals("Wrong ID 1", "f1", ids[0]);
            Assert.assertEquals("Wrong ID 2", "f2", ids[1]);
            Assert.assertEquals("Wrong ID 3", "f3", ids[2]);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testGetIdsNone() {
        Properties props = new Properties();
        
        try {
            String[] ids = FileConfig.getFileIds(props);
            
            Assert.assertEquals("Wrong number if IDs", 0, ids.length);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testConstructFileConfig() {
        String id = "f1";
        String fileName = "filename.nmea";
        Long pauseMillis = 12345L;
        
        Properties props = new Properties();
        props.setProperty(getFilePropName(id, FileConfig.FILENAME_PROPERTY), fileName);
        props.setProperty(getFilePropName(id, FileConfig.PAUSE_MILLIS_PROPERTY), pauseMillis.toString());
        
        try {
            FileConfig fc = new FileConfig(id, props);
            
            Assert.assertEquals("Wrong filename", fileName, fc.getFilename());
            Assert.assertEquals("Wrong pause millis", pauseMillis, fc.getPauseMillis());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testConstructFileConfigNoPauseMillis() {
        String id = "f1";
        String fileName = "filename.nmea";
        Long pauseMillis = 0L;
        
        Properties props = new Properties();
        props.setProperty(getFilePropName(id, FileConfig.FILENAME_PROPERTY), fileName);
        
        try {
            FileConfig fc = new FileConfig(id, props);
            
            Assert.assertEquals("Wrong filename", fileName, fc.getFilename());
            Assert.assertEquals("Wrong pause millis", pauseMillis, fc.getPauseMillis());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }
}
