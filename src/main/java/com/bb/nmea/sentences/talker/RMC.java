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
import com.bb.nmea.sentences.common.Direction;
import com.bb.nmea.sentences.common.Latitude;
import com.bb.nmea.sentences.common.Longitude;
import com.bb.nmea.sentences.common.SentenceDate;
import com.bb.nmea.sentences.common.Status;
import com.bb.nmea.sentences.common.UTCTime;

/**
 * RMC : Recommended Minimum Navigation Information
 * 
 * @author Scott Stanley
 */
public class RMC 
		extends TalkerSentence {
	private final UTCTime m_time;
	private final Status m_status;
	private final Latitude m_latitude;
	private final Direction m_latitudeDir;
	private final Longitude m_longitude;
	private final Direction m_longitudeDir;
	private final Float m_speedOverGroundKnots;
	private final Float m_courseOverGroundDegTrue;
	private final SentenceDate m_date;
	private final Float m_magVariation;
	private final Direction m_magVariationDir;
	
	public RMC(String rawSentence) {
		super(rawSentence);
		
		m_time = this.getFieldAsUTCTime(1);
		m_status = this.getFieldAsStatus(2);
		m_latitude = this.getFieldAsLatitude(3);
		m_latitudeDir = this.getFieldAsDirection(4);
		m_longitude = this.getFieldAsLongitude(5);
		m_longitudeDir = this.getFieldAsDirection(6);
		m_speedOverGroundKnots = this.getFieldAsFloat(7);
		m_courseOverGroundDegTrue = this.getFieldAsFloat(8);
		m_date = this.getFieldAsSentenceDate(9);
		m_magVariation = this.getFieldAsFloat(10);
		m_magVariationDir = this.getFieldAsDirection(11);
		
	}

	/**
	 * @return the time
	 */
	public UTCTime getTime() {
		return m_time;
	}

	/**
	 * @return the status
	 */
	public Status getStatus() {
		return m_status;
	}

	/**
	 * @return the latitude
	 */
	public Latitude getLatitude() {
		return m_latitude;
	}

	/**
	 * @return the latitudeDir
	 */
	public Direction getLatitudeDir() {
		return m_latitudeDir;
	}

	/**
	 * @return the longitude
	 */
	public Longitude getLongitude() {
		return m_longitude;
	}

	/**
	 * @return the longitudeDir
	 */
	public Direction getLongitudeDir() {
		return m_longitudeDir;
	}

	/**
	 * @return the speedOverGroundKnots
	 */
	public Float getSpeedOverGroundKnots() {
		return m_speedOverGroundKnots;
	}

	/**
	 * @return the courseOverGroundDegTrue
	 */
	public Float getCourseOverGroundDegTrue() {
		return m_courseOverGroundDegTrue;
	}

	/**
	 * @return the date
	 */
	public SentenceDate getDate() {
		return m_date;
	}

	/**
	 * @return the magVariation
	 */
	public Float getMagVariation() {
		return m_magVariation;
	}

	/**
	 * @return the magVariationDir
	 */
	public Direction getMagVariationDir() {
		return m_magVariationDir;
	}
}
