package com.bb.nmea.sentences.common;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GPSFixQualityTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void tesGpsSPSMode() {
		int rawValue = 1;
		
		GPSFixQuality quality = GPSFixQuality.getGPSFixQuality(rawValue);
		
		Assert.assertEquals("Wrong quality", GPSFixQuality.GPS_SPS_MODE, quality);
		Assert.assertEquals("Wrong raw value in quality", rawValue, quality.getRawFixQuality());
	}

	@Test
	public void testDGpsSPSMode() {
		int rawValue = 2;
		
		GPSFixQuality quality = GPSFixQuality.getGPSFixQuality(rawValue);
		
		Assert.assertEquals("Wrong quality", GPSFixQuality.DGPS_SPS_MODE, quality);
		Assert.assertEquals("Wrong raw value in quality", rawValue, quality.getRawFixQuality());
	}

	@Test
	public void testPPSMode() {
		int rawValue = 3;
		
		GPSFixQuality quality = GPSFixQuality.getGPSFixQuality(rawValue);
		
		Assert.assertEquals("Wrong quality", GPSFixQuality.PPS_MODE, quality);
		Assert.assertEquals("Wrong raw value in quality", rawValue, quality.getRawFixQuality());
	}

	@Test
	public void testRTKFixed() {
		int rawValue = 4;
		
		GPSFixQuality quality = GPSFixQuality.getGPSFixQuality(rawValue);
		
		Assert.assertEquals("Wrong quality", GPSFixQuality.REAL_TIME_KINEMATIC_FIX, quality);
		Assert.assertEquals("Wrong raw value in quality", rawValue, quality.getRawFixQuality());
	}

	@Test
	public void testRTKFloat() {
		int rawValue = 5;
		
		GPSFixQuality quality = GPSFixQuality.getGPSFixQuality(rawValue);
		
		Assert.assertEquals("Wrong quality", GPSFixQuality.REAL_TIME_KINEMATIC_FLOAT, quality);
		Assert.assertEquals("Wrong raw value in quality", rawValue, quality.getRawFixQuality());
	}

	@Test
	public void testDeadReckoning() {
		int rawValue = 6;
		
		GPSFixQuality quality = GPSFixQuality.getGPSFixQuality(rawValue);
		
		Assert.assertEquals("Wrong quality", GPSFixQuality.ESTIMATED_DEAD_RECKONING, quality);
		Assert.assertEquals("Wrong raw value in quality", rawValue, quality.getRawFixQuality());
	}

	@Test
	public void testManualMode() {
		int rawValue = 7;
		
		GPSFixQuality quality = GPSFixQuality.getGPSFixQuality(rawValue);
		
		Assert.assertEquals("Wrong quality", GPSFixQuality.MANUAL_INPUT_MODE, quality);
		Assert.assertEquals("Wrong raw value in quality", rawValue, quality.getRawFixQuality());
	}

	@Test
	public void testSimulationMode() {
		int rawValue = 8;
		
		GPSFixQuality quality = GPSFixQuality.getGPSFixQuality(rawValue);
		
		Assert.assertEquals("Wrong quality", GPSFixQuality.SIMULATION_MODE, quality);
		Assert.assertEquals("Wrong raw value in quality", rawValue, quality.getRawFixQuality());
	}


	@Test
	public void testUnsupported() {
		int rawValue = 98;
		
		try {
			GPSFixQuality.getGPSFixQuality(rawValue);
			Assert.fail("Should have thrown an exception");
		} catch (RuntimeException e) {
			// Ignore expected exception
		}
	}
}
