package com.bb.nmea.main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ConfigurationTest {
    private final String TEST_FILE = "testData/testfile.nmea";

    @Before
    public void setUp() throws Exception {
        System.setProperty(Configuration.NMEA_CONFIG_FILE_PROP, TEST_FILE);
    }

    @After
    public void tearDown() throws Exception {
        System.getProperties().remove(Configuration.NMEA_CONFIG_FILE_PROP);
        
        File f = new File(TEST_FILE);
        f.delete();
    }
    
    private void writeTestFile(final Properties props) {
        File oFile = new File(TEST_FILE);
        FileOutputStream oStrm = null;
        try {
            if (! oFile.getParentFile().exists()) {
                oFile.getParentFile().mkdirs();
            }
            
            oStrm = new FileOutputStream(oFile);
            props.store(oStrm, "");
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail("Failed writing properties file: " + e.getMessage());
        } finally {
            try {
                if (oStrm != null)
                    oStrm.close();
            } catch (IOException e) {}
        }
    }
    
    private Properties addPortProperties(final Properties props, final String[] ids, 
                                        final String[] descriptors, final Integer[] baudRates) {
        StringBuffer idsValue = new StringBuffer();
        for (int n = 0; n < ids.length; n++) {
            if (idsValue.length() > 0) {
                idsValue.append(",");
            }
            idsValue.append(ids[n]);
            
            props.setProperty(PortConfigTest.getPortPropName(ids[n], PortConfig.DESCRIPTOR_PROPERTY), descriptors[n]);
            props.setProperty(PortConfigTest.getPortPropName(ids[n], PortConfig.BAUDRATE_PROPERTY), baudRates[n].toString());
        }
        props.setProperty(PortConfigTest.getPortPropName(null, PortConfig.IDS_PROPERTY), idsValue.toString());
        
        return props;
    }
    
    private Properties addFileProperties(final Properties props, final String[] ids, 
                                         final String[] filenames, final Long[] pauseMillis) {
        StringBuffer idsValue = new StringBuffer();
        for (int n = 0; n < ids.length; n++) {
            if (idsValue.length() > 0) {
                idsValue.append(",");
            }
            idsValue.append(ids[n]);
            
            props.setProperty(FileConfigTest.getFilePropName(ids[n], FileConfig.FILENAME_PROPERTY), filenames[n]);
            props.setProperty(FileConfigTest.getFilePropName(ids[n], FileConfig.PAUSE_MILLIS_PROPERTY), pauseMillis[n].toString());
        }
        props.setProperty(FileConfigTest.getFilePropName(null, FileConfig.IDS_PROPERTY), idsValue.toString());
        
        return props;
    }
    
    private void validatePorts(final List<PortConfig> pCfgs, final String[] ids, 
                               final String[] descriptors, final Integer[] baudRates) {
        Assert.assertEquals("Wrong number of port configs", ids.length, pCfgs.size());
        
        for (int n = 0; n < ids.length; n++) {
            PortConfig pc = pCfgs.get(n);
            Assert.assertEquals("Incorrect descriptor " + n, descriptors[n], pc.getDescriptor());
            Assert.assertEquals("Incorrect baud " + n, baudRates[n], pc.getBaudRate());
        }
    }
    
    private void validateFiles(final List<FileConfig> pCfgs, final String[] ids, 
                               final String[] filenames, final Long[] pauseMillis) {
        Assert.assertEquals("Wrong number of file configs", ids.length, pCfgs.size());
        
        for (int n = 0; n < ids.length; n++) {
            FileConfig pc = pCfgs.get(n);
            Assert.assertEquals("Incorrect descriptor " + n, filenames[n], pc.getFilename());
            Assert.assertEquals("Incorrect baud " + n, pauseMillis[n], pc.getPauseMillis());
        }
    }

    @Test
    public void testSinglePort() {
        String[] ids = {"id1"};
        String[] descriptors = {"/dev/port1"};
        Integer[] baudRates = {1234};
        
        try {
            Properties props = new Properties();
            addPortProperties(props, ids, descriptors, baudRates);
            writeTestFile(props);
            
            Configuration conf = new Configuration();
            List<PortConfig> pCfgs = conf.getPortConfigs();
            
            validatePorts(pCfgs, ids, descriptors, baudRates);
            Assert.assertEquals("Incorrect number of files", 0, conf.getFileConfigs().size());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testMultiPort() {
        String[] ids = {"id1", "id2"};
        String[] descriptors = {"/dev/port1", "/dev/port2"};
        Integer[] baudRates = {1234, 4321};
        
        try {
            Properties props = new Properties();
            addPortProperties(props, ids, descriptors, baudRates);
            writeTestFile(props);
            
            Configuration conf = new Configuration();
            List<PortConfig> pCfgs = conf.getPortConfigs();
            
            validatePorts(pCfgs, ids, descriptors, baudRates);
            Assert.assertEquals("Incorrect number of files", 0, conf.getFileConfigs().size());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testSingleFile() {
        String[] ids = {"fid1"};
        String[] filenames = {"file1.nmea"};
        Long[] pauseMillis = {1234L};
        
        try {
            Properties props = new Properties();
            addFileProperties(props, ids, filenames, pauseMillis);
            writeTestFile(props);
            
            Configuration conf = new Configuration();
            List<FileConfig> pCfgs = conf.getFileConfigs();
            
            validateFiles(pCfgs, ids, filenames, pauseMillis);
            Assert.assertEquals("Incorrect number of ports", 0, conf.getPortConfigs().size());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testMultiFile() {
        String[] ids = {"fid1", "fid2"};
        String[] filenames = {"file1.nmea", "file2.nmea"};
        Long[] pauseMillis = {1234L, 4321L};
        
        try {
            Properties props = new Properties();
            addFileProperties(props, ids, filenames, pauseMillis);
            writeTestFile(props);
            
            Configuration conf = new Configuration();
            List<FileConfig> pCfgs = conf.getFileConfigs();
            
            validateFiles(pCfgs, ids, filenames, pauseMillis);
            Assert.assertEquals("Incorrect number of ports", 0, conf.getPortConfigs().size());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testMixedMulti() {
        String[] pids = {"id1", "id2"};
        String[] descriptors = {"/dev/port1", "/dev/port2"};
        Integer[] baudRates = {1234, 4321};

        String[] fids = {"fid1", "fid2"};
        String[] filenames = {"file1.nmea", "file2.nmea"};
        Long[] pauseMillis = {1234L, 4321L};

        try {
            Properties props = new Properties();
            addPortProperties(props, pids, descriptors, baudRates);
            addFileProperties(props, fids, filenames, pauseMillis);
            writeTestFile(props);
            
            Configuration conf = new Configuration();
            List<PortConfig> pCfgs = conf.getPortConfigs();
            validatePorts(pCfgs, pids, descriptors, baudRates);
            
            List<FileConfig> fCfgs = conf.getFileConfigs();
            validateFiles(fCfgs, fids, filenames, pauseMillis);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }
}
