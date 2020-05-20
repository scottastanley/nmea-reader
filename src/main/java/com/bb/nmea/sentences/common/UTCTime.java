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
package com.bb.nmea.sentences.common;

import java.time.LocalTime;

public class UTCTime {
    final Integer m_hour;
    final Integer m_minutes;
    final Float m_seconds;

    public UTCTime(final String str) {
        m_hour = Integer.valueOf(str.substring(0, 2));
        m_minutes = Integer.valueOf(str.substring(2, 4));
        m_seconds = Float.valueOf(str.substring(4, str.length()));
    }

    /**
     * @return the hour
     */
    public Integer getHour() {
        return m_hour;
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
     * Get the LocalTime instance for this UTC time.  
     * 
     * @return A LocalTime instance
     */
    public LocalTime getLocalTime() {
        Integer seconds = m_seconds.intValue();
        float fractSec = m_seconds - seconds;
        int nanoSeconds = (int) (fractSec * 1000000000);
        
        return LocalTime.of(m_hour, m_minutes, seconds, nanoSeconds);
    }
}
