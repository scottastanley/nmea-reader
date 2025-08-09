package com.bb.nmea.sentences.talker;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MDA_Test {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCase1() {
        String rawStr = "$WIMDA,29.9345,I,1.0137,B,18.9,C,25.2,C,15.3,23.1,12.3,C,238.1,T,223.4,M,6.7,N,3.4,M*25";
        
        try {
            long preTime = System.currentTimeMillis();
            MDA s = new MDA(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "WIMDA", s.getTag());
            Assert.assertEquals("Invalid talked ID", "WI", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "MDA", s.getSentenceId());
            Assert.assertEquals("Invalid checksum", "25", s.getChecksum());
            
            Assert.assertEquals("Invalid barometric pressure inches Hg", Float.valueOf(29.9345f), s.getBarometricPressureInchesHg());
            Assert.assertEquals("Invalid barometric pressure bars", Float.valueOf(1.0137f), s.getBarometricPressureBars());
            Assert.assertEquals("Invalid air temp C", Float.valueOf(18.9f), s.getAirTempDegreesC());
            Assert.assertEquals("Invalid water temp C", Float.valueOf(25.2F), s.getWaterTempDegreesC());
            Assert.assertEquals("Invalid relative humidity", Float.valueOf(15.3F), s.getRelativeHumidityPct());
            Assert.assertEquals("Invalid absolute humidity", Float.valueOf(23.1F), s.getAbsoluteHumidityPct());
            Assert.assertEquals("Invalid dew point C", Float.valueOf(12.3F), s.getDewPointDegreesC());
            Assert.assertEquals("Invalid wind dir true", Float.valueOf(238.1f), s.getWindDirDegreesTrue());
            Assert.assertEquals("Invalid wind dir mag", Float.valueOf(223.4f), s.getWindDirDegreesMag());
            Assert.assertEquals("Invalid wind speed knots", Float.valueOf(6.7f), s.getWindSpeedKnots());
            Assert.assertEquals("Invalid wind sp[eed m/s", Float.valueOf(3.4f), s.getWindSpeedMeterPerSec());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

	@Test
	public void testCase1_MissingFields() {
        String rawStr = "$WIMDA,29.9345,I,1.0137,B,18.9,C,,,,,,,238.1,T,223.4,M,6.7,N,3.4,M*27";
        
        try {
            long preTime = System.currentTimeMillis();
            MDA s = new MDA(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "WIMDA", s.getTag());
            Assert.assertEquals("Invalid talked ID", "WI", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "MDA", s.getSentenceId());
            Assert.assertEquals("Invalid checksum", "27", s.getChecksum());
            
            Assert.assertEquals("Invalid barometric pressure inches Hg", Float.valueOf(29.9345f), s.getBarometricPressureInchesHg());
            Assert.assertEquals("Invalid barometric pressure bars", Float.valueOf(1.0137f), s.getBarometricPressureBars());
            Assert.assertEquals("Invalid air temp C", Float.valueOf(18.9f), s.getAirTempDegreesC());
            Assert.assertNull("Invalid water temp C", s.getWaterTempDegreesC());
            Assert.assertNull("Invalid relative humidity", s.getRelativeHumidityPct());
            Assert.assertNull("Invalid absolute humidity", s.getAbsoluteHumidityPct());
            Assert.assertNull("Invalid dew point C", s.getDewPointDegreesC());
            Assert.assertEquals("Invalid wind dir true", Float.valueOf(238.1f), s.getWindDirDegreesTrue());
            Assert.assertEquals("Invalid wind dir mag", Float.valueOf(223.4f), s.getWindDirDegreesMag());
            Assert.assertEquals("Invalid wind speed knots", Float.valueOf(6.7f), s.getWindSpeedKnots());
            Assert.assertEquals("Invalid wind sp[eed m/s", Float.valueOf(3.4f), s.getWindSpeedMeterPerSec());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

}
