package com.bb.nmea.main;

import java.util.Properties;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PortConfigTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
    static String getPortPropName(final String id, final String propName) {
        String result = PortConfig.PORT_PREFIX;
        if (id != null) {
            result = result + id + ".";
        }
        return result + propName;
    }

    @Test
    public void testGetIdsSingle() {
        Properties props = new Properties();
        props.setProperty(getPortPropName(null, PortConfig.IDS_PROPERTY), "p1");
        
        try {
            String[] ids = PortConfig.getPortIds(props);
            
            Assert.assertEquals("Wrong number if IDs", 1, ids.length);
            Assert.assertEquals("Wrong ID 1", "p1", ids[0]);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testGetIdsMulti() {
        Properties props = new Properties();
        props.setProperty(getPortPropName(null, PortConfig.IDS_PROPERTY), "p1,p2,p3");
        
        try {
            String[] ids = PortConfig.getPortIds(props);
            
            Assert.assertEquals("Wrong number if IDs", 3, ids.length);
            Assert.assertEquals("Wrong ID 1", "p1", ids[0]);
            Assert.assertEquals("Wrong ID 2", "p2", ids[1]);
            Assert.assertEquals("Wrong ID 3", "p3", ids[2]);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }
    

    @Test
    public void testGetIdsNone() {
        Properties props = new Properties();
        
        try {
            String[] ids = PortConfig.getPortIds(props);
            
            Assert.assertEquals("Wrong number if IDs", 0, ids.length);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }
    

    @Test
    public void testConstructPortConfig() {
        String id = "/dev/foo";
        String descriptor = "filename.nmea";
        Integer baudRate = 12345;
        
        Properties props = new Properties();
        props.setProperty(getPortPropName(id, PortConfig.DESCRIPTOR_PROPERTY), descriptor);
        props.setProperty(getPortPropName(id, PortConfig.BAUDRATE_PROPERTY), baudRate.toString());
        
        try {
            PortConfig pc = new PortConfig(id, props);
            
            Assert.assertEquals("Wrong descriptor", descriptor, pc.getDescriptor());
            Assert.assertEquals("Wrong baud rate", baudRate, pc.getBaudRate());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }
    

    @Test
    public void testConstructPortConfigNoBaud() {
        String id = "/dev/foo";
        String descriptor = "filename.nmea";
        Integer baudRate = null;
        
        Properties props = new Properties();
        props.setProperty(getPortPropName(id, PortConfig.DESCRIPTOR_PROPERTY), descriptor);
        
        try {
            PortConfig pc = new PortConfig(id, props);
            
            Assert.assertEquals("Wrong descriptor", descriptor, pc.getDescriptor());
            Assert.assertEquals("Wrong baud rate", baudRate, pc.getBaudRate());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }
}
