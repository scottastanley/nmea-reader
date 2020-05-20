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

import java.time.LocalDateTime;

import com.bb.nmea.NMEASentence;

/**
 * ZDA: Time & Date. 
 * UTC, day, month, year and local time zone
 * 
 * @author Scott Stanley
 */
public class ZDA extends NMEASentence {
    private final Integer m_hours;
    private final Integer m_minutes;
    private final Float m_seconds;
    private final Integer m_day;
    private final Integer m_month;
    private final Integer m_year;
    private final Integer m_localZoneDescription;
    private final Integer m_localZoneMinutes;

    public ZDA(String rawSentence) {
        super(rawSentence);
        
        String timeStr = this.getField(1);
        m_hours = Integer.valueOf(timeStr.substring(0, 2));
        m_minutes = Integer.valueOf(timeStr.substring(2, 4));
        m_seconds = Float.valueOf(timeStr.substring(4, timeStr.length()));
        
        m_day = this.getFieldAsInteger(2);
        m_month = this.getFieldAsInteger(3);
        m_year = this.getFieldAsInteger(4);
        m_localZoneDescription = this.getFieldAsInteger(5);
        m_localZoneMinutes = this.getFieldAsInteger(6);
    }

    public LocalDateTime getLocalDateTime() {
        Integer seconds = m_seconds.intValue();
        float fractSec = m_seconds - seconds;
        int nanoSeconds = (int) (fractSec * 1000000000);
        
        return LocalDateTime.of(m_year, m_month, m_day, m_hours, m_minutes, seconds, nanoSeconds);
    }

    /**
     * @return the hours
     */
    public Integer getHours() {
        return m_hours;
    }

    /**
     * @return the minutes
     */
    public Integer getMinutes() {
        return m_minutes;
    }

    /**
     * @return the seconds
     */
    public Float getSeconds() {
        return m_seconds;
    }

    /**
     * @return the day
     */
    public Integer getDay() {
        return m_day;
    }

    /**
     * @return the month
     */
    public Integer getMonth() {
        return m_month;
    }

    /**
     * @return the year
     */
    public Integer getYear() {
        return m_year;
    }

    /**
     * Get the timezone hours offset.  
     *   00 to +/- 13 hours
     * 
     * @return the localZoneDescription
     */
    public Integer getLocalZoneDescription() {
        return m_localZoneDescription;
    }

    /**
     * Get the timezone offset minutes.  The same sign as the hour offset.  This field represents the 
     * fractional portion of hours offset.
     * 
     * @return the localZoneMinutes
     */
    public Integer getLocalZoneMinutes() {
        return m_localZoneMinutes;
    }
}
