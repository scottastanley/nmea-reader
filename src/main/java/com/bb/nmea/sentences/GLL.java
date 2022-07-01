/*
 * Copyright 2020 Scott Alan Stanley
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
package com.bb.nmea.sentences;

import com.bb.nmea.TalkerSentence;
import com.bb.nmea.sentences.common.Direction;
import com.bb.nmea.sentences.common.FAAModeIndicator;
import com.bb.nmea.sentences.common.Latitude;
import com.bb.nmea.sentences.common.Longitude;
import com.bb.nmea.sentences.common.Status;
import com.bb.nmea.sentences.common.UTCTime;

/**
 * GLL : Geographic Position, Latitude/Longitude and Time.
 * 
 * $GPGLL,3747.743,N,12220.080,W,190130,A,A*53
 * $GPGLL,3747.741,N,12220.083,W,190137.52,A,D*79
 * 
 * @author Scott Stanley
 */
public class GLL extends TalkerSentence {
    private final Latitude m_latitude;
    private final Direction m_latitudeDir;
    private final Longitude m_longitude;
    private final Direction m_longitudeDir;
    private final UTCTime m_utcTime;
    private final Status m_status;
    private final FAAModeIndicator m_faaModeIndicator;

    public GLL(String rawSentence) {
        super(rawSentence);
        
        m_latitude = this.getFieldAsLatitude(1);
        m_latitudeDir = this.getFieldAsDirection(2);
        m_longitude = this.getFieldAsLongitude(3);
        m_longitudeDir = this.getFieldAsDirection(4);
        m_utcTime = this.getFieldAsUTCTime(5);
        m_status = this.getFieldAsStatus(6);
        m_faaModeIndicator = this.getFieldAsFAAModeIndicator(7);
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
     * @return the utcTime
     */
    public UTCTime getUtcTime() {
        return m_utcTime;
    }

    /**
     * @return the status
     */
    public Status getStatus() {
        return m_status;
    }

    /**
     * @return the faaModeIndicator
     */
    public FAAModeIndicator getFaaModeIndicator() {
        return m_faaModeIndicator;
    }
}
