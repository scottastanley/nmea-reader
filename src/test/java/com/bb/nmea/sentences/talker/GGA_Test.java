package com.bb.nmea.sentences.talker;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.bb.nmea.sentences.common.Direction;
import com.bb.nmea.sentences.common.GPSFixQuality;
import com.bb.nmea.sentences.common.LatitudeTest;
import com.bb.nmea.sentences.common.LengthUnit;
import com.bb.nmea.sentences.common.LongitudeTest;
import com.bb.nmea.sentences.common.UtcTimeTest;

public class GGA_Test {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCase1() {
        String rawStr = "$GPGGA,193406,3806.969,N,12137.457,W,1,8,2.1,2,M,,M,,*6B";
        
        try {
            long preTime = System.currentTimeMillis();
            GGA s = new GGA(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "GPGGA", s.getTag());
            Assert.assertEquals("Invalid talked ID", "GP", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "GGA", s.getSentenceId());
            Assert.assertEquals("Invalid checksum", "6B", s.getChecksum());
            
            UtcTimeTest.validateUTCTime(19,  34,  06.00F,  s.getUtcTime());
            
            LatitudeTest.validateLatitude(38, 06.969F, s.getLatitude(), s.getLatitudeDir());
            Assert.assertEquals("Wrong latitude direction", Direction.NORTH, s.getLatitudeDir());
            
            LongitudeTest.validateLongitude(121, 37.457F, s.getLongitude(), s.getLongitudeDir());
            Assert.assertEquals("Wrong longitude direction", Direction.WEST, s.getLongitudeDir());
            
            Assert.assertEquals("Wrong fix quality", GPSFixQuality.GPS_SPS_MODE, s.getFixQuality());
            Assert.assertEquals("Wrong satellites tracked", Integer.valueOf(8), s.getSatellitesTracked());
            Assert.assertEquals("Wrong horizontal dilution", Float.valueOf(2.1F), s.getHorizontalDilution());
            
            Assert.assertEquals("Wrong altitude", Float.valueOf(2F), s.getAltitude());
            Assert.assertEquals("Wrong altitude unit", LengthUnit.METER, s.getAltitudeUnits());
            
            Assert.assertNull("Wrong geoid sep", s.getGeoIdSeparation());
            Assert.assertEquals("Wrong geoid sep unit", LengthUnit.METER, s.getGeoIdSeparationUnits());
            
            Assert.assertNull("Wrong dgps age", s.getAgeOfDGPSCorrelationSec());
            Assert.assertNull("Wrong dgps ref id", s.getDgpsReferenceStationId());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }

	@Test
	public void testCase2() {
        String rawStr = "$GPGGA,053050,3806.969,N,12137.455,W,1,11,1.6,3,M,,M,,*5E";
        
        try {
            long preTime = System.currentTimeMillis();
            GGA s = new GGA(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "GPGGA", s.getTag());
            Assert.assertEquals("Invalid talked ID", "GP", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "GGA", s.getSentenceId());
            Assert.assertEquals("Invalid checksum", "5E", s.getChecksum());
            
            UtcTimeTest.validateUTCTime(05,  30,  50.00F,  s.getUtcTime());
            
            LatitudeTest.validateLatitude(38, 06.969F, s.getLatitude(), s.getLatitudeDir());
            Assert.assertEquals("Wrong latitude direction", Direction.NORTH, s.getLatitudeDir());
            
            LongitudeTest.validateLongitude(121, 37.455F, s.getLongitude(), s.getLongitudeDir());
            Assert.assertEquals("Wrong longitude direction", Direction.WEST, s.getLongitudeDir());
            
            Assert.assertEquals("Wrong fix quality", GPSFixQuality.GPS_SPS_MODE, s.getFixQuality());
            Assert.assertEquals("Wrong satellites tracked", Integer.valueOf(11), s.getSatellitesTracked());
            Assert.assertEquals("Wrong horizontal dilution", Float.valueOf(1.6F), s.getHorizontalDilution());
            
            Assert.assertEquals("Wrong altitude", Float.valueOf(3F), s.getAltitude());
            Assert.assertEquals("Wrong altitude unit", LengthUnit.METER, s.getAltitudeUnits());
            
            Assert.assertNull("Wrong geoid sep", s.getGeoIdSeparation());
            Assert.assertEquals("Wrong geoid sep unit", LengthUnit.METER, s.getGeoIdSeparationUnits());
            
            Assert.assertNull("Wrong dgps age", s.getAgeOfDGPSCorrelationSec());
            Assert.assertNull("Wrong dgps ref id", s.getDgpsReferenceStationId());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }	

	@Test
	public void testCase3() {
        String rawStr = "$GPGGA,045727,3806.9704,N,12137.4571,W,2,11,0.8,-0.5,M,,,,*25";
        
        try {
            long preTime = System.currentTimeMillis();
            GGA s = new GGA(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "GPGGA", s.getTag());
            Assert.assertEquals("Invalid talked ID", "GP", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "GGA", s.getSentenceId());
            Assert.assertEquals("Invalid checksum", "25", s.getChecksum());
            
            UtcTimeTest.validateUTCTime(04,  57,  27.00F,  s.getUtcTime());
            
            LatitudeTest.validateLatitude(38, 06.9704F, s.getLatitude(), s.getLatitudeDir());
            Assert.assertEquals("Wrong latitude direction", Direction.NORTH, s.getLatitudeDir());
            
            LongitudeTest.validateLongitude(121, 37.4571F, s.getLongitude(), s.getLongitudeDir());
            Assert.assertEquals("Wrong longitude direction", Direction.WEST, s.getLongitudeDir());
            
            Assert.assertEquals("Wrong fix quality", GPSFixQuality.DGPS_SPS_MODE, s.getFixQuality());
            Assert.assertEquals("Wrong satellites tracked", Integer.valueOf(11), s.getSatellitesTracked());
            Assert.assertEquals("Wrong horizontal dilution", Float.valueOf(0.8F), s.getHorizontalDilution());
            
            Assert.assertEquals("Wrong altitude", Float.valueOf(-0.5F), s.getAltitude());
            Assert.assertEquals("Wrong altitude unit", LengthUnit.METER, s.getAltitudeUnits());
            
            Assert.assertNull("Wrong geoid sep", s.getGeoIdSeparation());
            Assert.assertNull("Wrong geoid sep unit", s.getGeoIdSeparationUnits());
            
            Assert.assertNull("Wrong dgps age", s.getAgeOfDGPSCorrelationSec());
            Assert.assertNull("Wrong dgps ref id", s.getDgpsReferenceStationId());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
    }
	
}
