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
package com.bb.nmea.sentences.talker;

import com.bb.nmea.TalkerSentence;

/**
 * MDA : Meteorological Composite
 * 
 * Obsolete as of 2009
 * 
 * References
 * NMEA Revealed (https://gpsd.gitlab.io/gpsd/NMEA.html)
 * 
 * @author Scott Stanley
 */
public class MDA 
		extends TalkerSentence {
	private final Float m_barometricPressureInchesHg;
	private final Float m_barometricPressureBars;
	private final Float m_airTempDegreesC;
	private final Float m_waterTempDegreesC;
	private final Float m_relativeHumidityPct;
	private final Float m_absoluteHumidityPct;
	private final Float m_dewPointDegreesC;
	private final Float m_windDirDegreesTrue;
	private final Float m_windDirDegreesMag;
	private final Float m_windSpeedKnots;
	private final Float m_windSpeedMeterPerSec;
	

	public MDA(final String rawSentence) {
		super(rawSentence);

		m_barometricPressureInchesHg = this.getFieldAsFloat(1);
		m_barometricPressureBars = this.getFieldAsFloat(3);
		m_airTempDegreesC = this.getFieldAsFloat(5);
		m_waterTempDegreesC = this.getFieldAsFloat(7);
		m_relativeHumidityPct = this.getFieldAsFloat(9);
		m_absoluteHumidityPct = this.getFieldAsFloat(10);
		m_dewPointDegreesC = this.getFieldAsFloat(11);
		m_windDirDegreesTrue = this.getFieldAsFloat(13);
		m_windDirDegreesMag = this.getFieldAsFloat(15);
		m_windSpeedKnots = this.getFieldAsFloat(17);
		m_windSpeedMeterPerSec = this.getFieldAsFloat(19);
	}


	/**
	 * @return the barometricPressureInchesHg
	 */
	public Float getBarometricPressureInchesHg() {
		return m_barometricPressureInchesHg;
	}


	/**
	 * @return the barometricPressureBars
	 */
	public Float getBarometricPressureBars() {
		return m_barometricPressureBars;
	}


	/**
	 * @return the airTempDegreesC
	 */
	public Float getAirTempDegreesC() {
		return m_airTempDegreesC;
	}


	/**
	 * @return the waterTempDegreesC
	 */
	public Float getWaterTempDegreesC() {
		return m_waterTempDegreesC;
	}


	/**
	 * @return the relativeHumidityPct
	 */
	public Float getRelativeHumidityPct() {
		return m_relativeHumidityPct;
	}


	/**
	 * @return the absoluteHumidityPct
	 */
	public Float getAbsoluteHumidityPct() {
		return m_absoluteHumidityPct;
	}


	/**
	 * @return the dewPointDegreesC
	 */
	public Float getDewPointDegreesC() {
		return m_dewPointDegreesC;
	}


	/**
	 * @return the windDirDegreesTrue
	 */
	public Float getWindDirDegreesTrue() {
		return m_windDirDegreesTrue;
	}


	/**
	 * @return the windDirDegreesMag
	 */
	public Float getWindDirDegreesMag() {
		return m_windDirDegreesMag;
	}


	/**
	 * @return the windSpeedKnots
	 */
	public Float getWindSpeedKnots() {
		return m_windSpeedKnots;
	}


	/**
	 * @return the windSpeedMeterPerSec
	 */
	public Float getWindSpeedMeterPerSec() {
		return m_windSpeedMeterPerSec;
	}

}
