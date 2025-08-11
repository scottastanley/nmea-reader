package com.bb.nmea.sentences.talker;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.bb.nmea.sentences.common.Direction;
import com.bb.nmea.sentences.common.LatitudeTest;
import com.bb.nmea.sentences.common.LongitudeTest;
import com.bb.nmea.sentences.common.SentenceDate;
import com.bb.nmea.sentences.common.Status;
import com.bb.nmea.sentences.common.UtcTimeTest;

public class RMC_Test {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCase1() {
        String rawStr = "$GPRMC,051416,A,3806.968,N,12137.454,W,0.0,199.0,191205,15.1,E*50";
        
        try {
            long preTime = System.currentTimeMillis();
            RMC s = new RMC(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "GPRMC", s.getTag());
            Assert.assertEquals("Invalid talked ID", "GP", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "RMC", s.getSentenceId());
            Assert.assertEquals("Invalid checksum", "50", s.getChecksum());
            
            
            UtcTimeTest.validateUTCTime(05, 14, 16F, s.getTime());
            Assert.assertEquals("Invalid status", Status.VALID, s.getStatus());
            
            
            
            LatitudeTest.validateLatitude(38, 06.968F, s.getLatitude(), s.getLatitudeDir());
            Assert.assertEquals("Invalid latitude direction", Direction.NORTH, s.getLatitudeDir());
            
            LongitudeTest.validateLongitude(121, 37.454F, s.getLongitude(), s.getLongitudeDir());
            Assert.assertEquals("Invalid longitude direction", Direction.WEST, s.getLongitudeDir());

            Assert.assertEquals("Invalid speed over ground", Float.valueOf(0.0F), s.getSpeedOverGroundKnots());
            Assert.assertEquals("Invalid course over ground", Float.valueOf(199.0F), s.getCourseOverGroundDegTrue());
            
            Assert.assertEquals("Invalid date",  new SentenceDate("191205"), s.getDate());
            
            Assert.assertEquals("Invalid magnetic variation", Float.valueOf(15.1F), s.getMagVariation());
            Assert.assertEquals("Invalid magnetic variation direction", Direction.EAST, s.getMagVariationDir());

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
	}

	@Test
	public void testCase2() {
        String rawStr = "$GPRMC,142852,V,1315.253,S,05321.231,E,5.1,126.8,020424,10.7,W*5E";
        
        try {
            long preTime = System.currentTimeMillis();
            RMC s = new RMC(rawStr);
            long postTime = System.currentTimeMillis();
            
            Assert.assertEquals("Incorrect raw NMEA sentence", rawStr, s.getRawSentence());
            Assert.assertTrue("Invalid collected timestamp", 
                    preTime <= s.getCollectedTimestamp() && s.getCollectedTimestamp() <= postTime);
            
            Assert.assertEquals("Invalid tag", "GPRMC", s.getTag());
            Assert.assertEquals("Invalid talked ID", "GP", s.getTalkerId());
            Assert.assertEquals("Invalid type code", "RMC", s.getSentenceId());
            Assert.assertEquals("Invalid checksum", "5E", s.getChecksum());
            
            
            UtcTimeTest.validateUTCTime(14, 28, 52F, s.getTime());
            Assert.assertEquals("Invalid status", Status.INVALID, s.getStatus());
            
            
            
            LatitudeTest.validateLatitude(13, 15.253F, s.getLatitude(), s.getLatitudeDir());
            Assert.assertEquals("Invalid latitude direction", Direction.SOUTH, s.getLatitudeDir());
            
            LongitudeTest.validateLongitude(53, 21.231F, s.getLongitude(), s.getLongitudeDir());
            Assert.assertEquals("Invalid longitude direction", Direction.EAST, s.getLongitudeDir());

            Assert.assertEquals("Invalid speed over ground", Float.valueOf(5.1F), s.getSpeedOverGroundKnots());
            Assert.assertEquals("Invalid course over ground", Float.valueOf(126.8F), s.getCourseOverGroundDegTrue());
            
            Assert.assertEquals("Invalid date",  new SentenceDate("020424"), s.getDate());
            
            Assert.assertEquals("Invalid magnetic variation", Float.valueOf(10.7F), s.getMagVariation());
            Assert.assertEquals("Invalid magnetic variation direction", Direction.WEST, s.getMagVariationDir());

        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Caught unexpected exception: " + e.getMessage());
        }
	}

}
