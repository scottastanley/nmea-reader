/*
 * Copyright 2025 Scott Alan Stanley
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bb.nmea.sentences.common;

public enum GPSFixQuality {
	INVALID(0),
	GPS_SPS_MODE(1),
	DGPS_SPS_MODE(2),
	PPS_MODE(3),
	REAL_TIME_KINEMATIC_FIX(4),
	REAL_TIME_KINEMATIC_FLOAT(5),
	ESTIMATED_DEAD_RECKONING(6),
	MANUAL_INPUT_MODE(7),
	SIMULATION_MODE(8);
	

	private final int m_rawFixQuality;
	
	private GPSFixQuality(final int rawFixQuality) {
		m_rawFixQuality = rawFixQuality;
	}
	
	public int getRawFixQuality() {
		return m_rawFixQuality;
	}
	
	public static GPSFixQuality getGPSFixQuality(final int rawFixQuality) {
		
		if (rawFixQuality == GPSFixQuality.INVALID.m_rawFixQuality) {
			return GPSFixQuality.INVALID;
		} else if (rawFixQuality == GPSFixQuality.GPS_SPS_MODE.m_rawFixQuality) {
			return GPSFixQuality.GPS_SPS_MODE;
		} else if (rawFixQuality == GPSFixQuality.DGPS_SPS_MODE.m_rawFixQuality) {
			return GPSFixQuality.DGPS_SPS_MODE;
		} else if (rawFixQuality == GPSFixQuality.PPS_MODE.m_rawFixQuality) {
			return GPSFixQuality.PPS_MODE;
		} else if (rawFixQuality == GPSFixQuality.REAL_TIME_KINEMATIC_FIX.m_rawFixQuality) {
			return GPSFixQuality.REAL_TIME_KINEMATIC_FIX;
		} else if (rawFixQuality == GPSFixQuality.REAL_TIME_KINEMATIC_FLOAT.m_rawFixQuality) {
			return GPSFixQuality.REAL_TIME_KINEMATIC_FLOAT;
		} else if (rawFixQuality == GPSFixQuality.ESTIMATED_DEAD_RECKONING.m_rawFixQuality) {
			return GPSFixQuality.ESTIMATED_DEAD_RECKONING;
		} else if (rawFixQuality == GPSFixQuality.MANUAL_INPUT_MODE.m_rawFixQuality) {
			return GPSFixQuality.MANUAL_INPUT_MODE;
		} else if (rawFixQuality == GPSFixQuality.SIMULATION_MODE.m_rawFixQuality) {
			return GPSFixQuality.SIMULATION_MODE;
		} else {
            throw new RuntimeException("Unsupported rawFixQuality: " + rawFixQuality);
		}
	}
}
