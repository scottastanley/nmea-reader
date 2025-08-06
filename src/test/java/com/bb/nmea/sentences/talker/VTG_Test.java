package com.bb.nmea.sentences.talker;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.bb.nmea.sentences.common.FAAModeIndicator;

public class VTG_Test {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testFullyPopulated_DifferentialMode_post_v2_3() {
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
            Assert.assertEquals("Invalid type code", "VTG", s.getSentenceId());
            Assert.assertEquals("Invalid checksum", "25", s.getChecksum());
            
            Assert.assertEquals("Invalid true course", Float.valueOf(77.2f), s.getCourseOverGroundDegrTrue());
            Assert.assertEquals("Invalid magnetic course", Float.valueOf(64.0f), s.getCourseOverGroundDegrMagnetic());
            Assert.assertEquals("Invalid true course", Float.valueOf(0.1f), s.getSpeedOverGroundKnots());
            Assert.assertEquals("Invalid true course", Float.valueOf(0.2f), s.getSpeedOverGroundKmPerHr());
            Assert.assertEquals("Invalid FAA mode", FAAModeIndicator.DIFFERENTIAL_MODE, s.getFaaModeIndicator());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testFullyPopulated_AutonomousMode_post_v2_3() {
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
            Assert.assertEquals("Invalid type code", "VTG", s.getSentenceId());
            Assert.assertEquals("Invalid checksum", "25", s.getChecksum());
            
            Assert.assertEquals("Invalid true course", Float.valueOf(77.2f), s.getCourseOverGroundDegrTrue());
            Assert.assertEquals("Invalid magnetic course", Float.valueOf(64.0f), s.getCourseOverGroundDegrMagnetic());
            Assert.assertEquals("Invalid true course", Float.valueOf(0.1f), s.getSpeedOverGroundKnots());
            Assert.assertEquals("Invalid true course", Float.valueOf(0.2f), s.getSpeedOverGroundKmPerHr());
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
            Assert.assertEquals("Invalid type code", "VTG", s.getSentenceId());
            Assert.assertEquals("Invalid checksum", "25", s.getChecksum());
            
            Assert.assertEquals("Invalid true course", Float.valueOf(77.2f), s.getCourseOverGroundDegrTrue());
            Assert.assertEquals("Invalid magnetic course", Float.valueOf(64.0f), s.getCourseOverGroundDegrMagnetic());
            Assert.assertEquals("Invalid true course", Float.valueOf(0.1f), s.getSpeedOverGroundKnots());
            Assert.assertEquals("Invalid true course", Float.valueOf(0.2f), s.getSpeedOverGroundKmPerHr());
            Assert.assertEquals("Invalid FAA mode", null, s.getFaaModeIndicator());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }
    
    @Test
    public void testFailureCase_1_pre_v2_3() {
        String rawStr = "$GPVTG,252.8,T,238.1,M,0.1,N,0.1,K*4B";
        
        try {
            long preTime = System.currentTimeMillis();
            VTG s = new VTG(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "GPVTG", s.getTag());
            Assert.assertEquals("Invalid talked ID", "GP", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "VTG", s.getSentenceId());
            Assert.assertEquals("Invalid checksum", "4B", s.getChecksum());
            
            Assert.assertEquals("Invalid true course", Float.valueOf(252.8f), s.getCourseOverGroundDegrTrue());
            Assert.assertEquals("Invalid magnetic course", Float.valueOf(238.1f), s.getCourseOverGroundDegrMagnetic());
            Assert.assertEquals("Invalid speed knots", Float.valueOf(0.1f), s.getSpeedOverGroundKnots());
            Assert.assertEquals("Invalid speed km/hr", Float.valueOf(0.1f), s.getSpeedOverGroundKmPerHr());
            Assert.assertNull("Invalid FAA mode", s.getFaaModeIndicator());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }
    
    @Test
    public void testFailureCase_2_pre_v2_3() {
        String rawStr = "$GPVTG,258.0,T,243.3,M,0.1,N,0.1,K*47";
        
        try {
            long preTime = System.currentTimeMillis();
            VTG s = new VTG(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "GPVTG", s.getTag());
            Assert.assertEquals("Invalid talked ID", "GP", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "VTG", s.getSentenceId());
            Assert.assertEquals("Invalid checksum", "47", s.getChecksum());
            
            Assert.assertEquals("Invalid true course", Float.valueOf(258.0f), s.getCourseOverGroundDegrTrue());
            Assert.assertEquals("Invalid magnetic course", Float.valueOf(243.3f), s.getCourseOverGroundDegrMagnetic());
            Assert.assertEquals("Invalid speed knots", Float.valueOf(0.1f), s.getSpeedOverGroundKnots());
            Assert.assertEquals("Invalid speed km/hr", Float.valueOf(0.1f), s.getSpeedOverGroundKmPerHr());
            Assert.assertNull("Invalid FAA mode", s.getFaaModeIndicator());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }
}
