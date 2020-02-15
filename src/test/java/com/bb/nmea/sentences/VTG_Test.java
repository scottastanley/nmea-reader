package com.bb.nmea.sentences;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bb.nmea.sentences.common.FAAModeIndicator;

import junit.framework.Assert;

public class VTG_Test {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testFullyPopulated_DifferentialMode() {
        String rawStr = "$GPVTG,077.2,T,064.0,M,0.1,N,0.2,K,D*25";
        
        try {
            long preTime = System.currentTimeMillis();
            VTG s = new VTG(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "GPVTG", s.getTag());
            Assert.assertEquals("Invalid talked ID", "GP", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "VTG", s.getTypeCode());
            Assert.assertEquals("Invalid checksum", "25", s.getChecksum());
            
            Assert.assertEquals("Invalid true course", new Float(77.2), s.getCourseOverGroundDegrTrue());
            Assert.assertEquals("Invalid magnetic course", new Float(64.0), s.getCourseOverGroundDegrMagnetic());
            Assert.assertEquals("Invalid true course", new Float(0.1), s.getSpeedOverGroundKnots());
            Assert.assertEquals("Invalid true course", new Float(0.2), s.getSpeedOverGroundKmPerHr());
            Assert.assertEquals("Invalid FAA mode", FAAModeIndicator.DIFFERENTIAL_MODE, s.getFaaModeIndicator());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testFullyPopulated_AutonomousMode() {
        String rawStr = "$GPVTG,077.2,T,064.0,M,0.1,N,0.2,K,A*25";
        
        try {
            long preTime = System.currentTimeMillis();
            VTG s = new VTG(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "GPVTG", s.getTag());
            Assert.assertEquals("Invalid talked ID", "GP", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "VTG", s.getTypeCode());
            Assert.assertEquals("Invalid checksum", "25", s.getChecksum());
            
            Assert.assertEquals("Invalid true course", new Float(77.2), s.getCourseOverGroundDegrTrue());
            Assert.assertEquals("Invalid magnetic course", new Float(64.0), s.getCourseOverGroundDegrMagnetic());
            Assert.assertEquals("Invalid true course", new Float(0.1), s.getSpeedOverGroundKnots());
            Assert.assertEquals("Invalid true course", new Float(0.2), s.getSpeedOverGroundKmPerHr());
            Assert.assertEquals("Invalid FAA mode", FAAModeIndicator.AUTONOMOUS_MODE, s.getFaaModeIndicator());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testLegacyFormat() {
        String rawStr = "$GPVTG,077.2,064.0,0.1,0.2*25";
        
        try {
            long preTime = System.currentTimeMillis();
            VTG s = new VTG(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "GPVTG", s.getTag());
            Assert.assertEquals("Invalid talked ID", "GP", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "VTG", s.getTypeCode());
            Assert.assertEquals("Invalid checksum", "25", s.getChecksum());
            
            Assert.assertEquals("Invalid true course", new Float(77.2), s.getCourseOverGroundDegrTrue());
            Assert.assertEquals("Invalid magnetic course", new Float(64.0), s.getCourseOverGroundDegrMagnetic());
            Assert.assertEquals("Invalid true course", new Float(0.1), s.getSpeedOverGroundKnots());
            Assert.assertEquals("Invalid true course", new Float(0.2), s.getSpeedOverGroundKmPerHr());
            Assert.assertEquals("Invalid FAA mode", null, s.getFaaModeIndicator());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }
}
