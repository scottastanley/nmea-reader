package com.bb.nmea.sentences;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.bb.nmea.sentences.common.SpeedUnits;
import com.bb.nmea.sentences.common.Status;
import com.bb.nmea.sentences.common.WindReference;

import junit.framework.Assert;

public class MWV_Test {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testRelativeKnots() {
        String rawStr = "$ECMWV,123.5,R,1.2,N,A*3D";
        
        try {
            long preTime = System.currentTimeMillis();
            MWV s = new MWV(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "ECMWV", s.getTag());
            Assert.assertEquals("Invalid talked ID", "EC", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "MWV", s.getTypeCode());
            Assert.assertEquals("Invalid checksum", "3D", s.getChecksum());
            
            Assert.assertEquals("Invalid wind angle", new Float(123.5), s.getWindAngle());
            Assert.assertEquals("Invalid wind reference", WindReference.RELATIVE, s.getWindReference());
            Assert.assertEquals("Invalid wind speed", new Float(1.2), s.getWindSpeed());
            Assert.assertEquals("Invalid speed units", SpeedUnits.KNOTS, s.getSpeedUnits());
            Assert.assertEquals("Invalid status", Status.VALID, s.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testTrueKnots() {
        String rawStr = "$ECMWV,359.9,T,17.3,N,A*3D";
        
        try {
            long preTime = System.currentTimeMillis();
            MWV s = new MWV(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "ECMWV", s.getTag());
            Assert.assertEquals("Invalid talked ID", "EC", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "MWV", s.getTypeCode());
            Assert.assertEquals("Invalid checksum", "3D", s.getChecksum());
            
            Assert.assertEquals("Invalid wind angle", new Float(359.9), s.getWindAngle());
            Assert.assertEquals("Invalid wind reference", WindReference.TRUE, s.getWindReference());
            Assert.assertEquals("Invalid wind speed", new Float(17.3), s.getWindSpeed());
            Assert.assertEquals("Invalid speed units", SpeedUnits.KNOTS, s.getSpeedUnits());
            Assert.assertEquals("Invalid status", Status.VALID, s.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testRelativeKmPerHr() {
        String rawStr = "$ECMWV,231.1,R,8.6,K,A*3D";
        
        try {
            long preTime = System.currentTimeMillis();
            MWV s = new MWV(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "ECMWV", s.getTag());
            Assert.assertEquals("Invalid talked ID", "EC", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "MWV", s.getTypeCode());
            Assert.assertEquals("Invalid checksum", "3D", s.getChecksum());
            
            Assert.assertEquals("Invalid wind angle", new Float(231.1), s.getWindAngle());
            Assert.assertEquals("Invalid wind reference", WindReference.RELATIVE, s.getWindReference());
            Assert.assertEquals("Invalid wind speed", new Float(8.6), s.getWindSpeed());
            Assert.assertEquals("Invalid speed units", SpeedUnits.KM_PER_HR, s.getSpeedUnits());
            Assert.assertEquals("Invalid status", Status.VALID, s.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

    @Test
    public void testTrueKmPerHr() {
        String rawStr = "$ECMWV,13.2,T,12.7,K,A*3D";
        
        try {
            long preTime = System.currentTimeMillis();
            MWV s = new MWV(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "ECMWV", s.getTag());
            Assert.assertEquals("Invalid talked ID", "EC", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "MWV", s.getTypeCode());
            Assert.assertEquals("Invalid checksum", "3D", s.getChecksum());
            
            Assert.assertEquals("Invalid wind angle", new Float(13.2), s.getWindAngle());
            Assert.assertEquals("Invalid wind reference", WindReference.TRUE, s.getWindReference());
            Assert.assertEquals("Invalid wind speed", new Float(12.7), s.getWindSpeed());
            Assert.assertEquals("Invalid speed units", SpeedUnits.KM_PER_HR, s.getSpeedUnits());
            Assert.assertEquals("Invalid status", Status.VALID, s.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }
}
