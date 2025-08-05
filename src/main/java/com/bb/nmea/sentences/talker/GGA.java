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
import com.bb.nmea.sentences.common.GPSFixQuality;
import com.bb.nmea.sentences.common.Latitude;
import com.bb.nmea.sentences.common.LengthUnit;
import com.bb.nmea.sentences.common.Longitude;
import com.bb.nmea.sentences.common.UTCTime;

/**
 * GGA : Global Positioning System Fix Data.
 * 
 * $GPGGA,193406,3806.969,N,12137.457,W,1,8,2.1,2,M,,M,,*6B
 * $GPGGA,045056,3806.966,N,12137.452,W,1,9,1.7,12,M,,M,,*5F
 * $GPGGA,053050,3806.969,N,12137.455,W,1,11,1.6,3,M,,M,,*5E
 * 
 * @author Scott Stanley
 */
public class GGA extends TalkerSentence {
    private final UTCTime m_utcTime;
	private final Latitude m_latitude;
    private final Direction m_latitudeDir;
    private final Longitude m_longitude;
    private final Direction m_longitudeDir;
    private final GPSFixQuality m_fixQuality;
    private final Integer m_satellitesTracked;
    private final Float m_horizontalDilution;
    private final Float m_altitude;
    private final LengthUnit m_altitudeUnits;
    private final Float m_geoIdSeparation;
    private final LengthUnit m_geoIdSeparationUnits;
    private final Integer m_ageOfDGPSCorrelationSec;
    private final String m_dgpsReferenceStationId;
    

    public GGA(final String rawSentence) {
		super(rawSentence);
		
        m_utcTime = this.getFieldAsUTCTime(1);
        m_latitude = this.getFieldAsLatitude(2);
        m_latitudeDir = this.getFieldAsDirection(3);
        m_longitude = this.getFieldAsLongitude(4);
        m_longitudeDir = this.getFieldAsDirection(5);
        m_fixQuality = this.getFieldAsGpsFixQuality(6);
        m_satellitesTracked = this.getFieldAsInteger(7);
        m_horizontalDilution = this.getFieldAsFloat(8);
        m_altitude = this.getFieldAsFloat(9);
        m_altitudeUnits = this.getFieldAsLengthUnit(10);
        m_geoIdSeparation = this.getFieldAsFloat(11);
        m_geoIdSeparationUnits = this.getFieldAsLengthUnit(12);
        m_ageOfDGPSCorrelationSec = this.getFieldAsInteger(13);
        m_dgpsReferenceStationId = this.getField(14);
	}


	/**
	 * @return the utcTime
	 */
	public UTCTime getUtcTime() {
		return m_utcTime;
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
	 * @return the fixQuality
	 */
	public GPSFixQuality getFixQuality() {
		return m_fixQuality;
	}


	/**
	 * @return the satellitesTracked
	 */
	public Integer getSatellitesTracked() {
		return m_satellitesTracked;
	}


	/**
	 * @return the horizontalDilution
	 */
	public Float getHorizontalDilution() {
		return m_horizontalDilution;
	}


	/**
	 * @return the altitude
	 */
	public Float getAltitude() {
		return m_altitude;
	}


	/**
	 * @return the altitudeUnits
	 */
	public LengthUnit getAltitudeUnits() {
		return m_altitudeUnits;
	}


	/**
	 * @return the geoIdSeparation
	 */
	public Float getGeoIdSeparation() {
		return m_geoIdSeparation;
	}


	/**
	 * @return the geoIdSeparationUnits
	 */
	public LengthUnit getGeoIdSeparationUnits() {
		return m_geoIdSeparationUnits;
	}


	/**
	 * @return the ageOfDGPSCorrelationSec
	 */
	public Integer getAgeOfDGPSCorrelationSec() {
		return m_ageOfDGPSCorrelationSec;
	}


	/**
	 * @return the dgpsReferenceStationId
	 */
	public String getDgpsReferenceStationId() {
		return m_dgpsReferenceStationId;
	}

}
