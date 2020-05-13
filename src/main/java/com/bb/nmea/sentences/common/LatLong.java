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

/**
 * A common utility class which can be used by any NMEA sentence class to represent 
 * a latitude or longitude value. This class contains the logic defined by the 
 * NMEA specification for parsing a string representation of a latitude or longitude 
 * value in to the degrees and minutes.
 * 
 * @author Scott Stanley
 */
abstract class LatLong {
    final Integer m_degrees;
    final Float m_minutes;

    /**
     * Construct a LatLong value from the provided string value.
     * 
     * @param str
     */
    LatLong(final String str) {
        m_degrees = Integer.valueOf(str.substring(0, str.indexOf(".") - 2));
        m_minutes = Float.valueOf(str.substring(str.indexOf(".") - 2, str.length()));
    }
    
    /**
     * Get the whole degrees
     * 
     * @return 
     */
    public Integer getDegrees() {
        return m_degrees;
    }
    
    /**
     * Get the fractional minutes.
     * 
     * @return 
     */
    public Float getMinutes() {
        return m_minutes;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (LatLong.class.isInstance(obj)) {
            LatLong other = LatLong.class.cast(obj);
            
            return m_degrees == other.m_degrees && 
                    m_minutes == other.getMinutes();
        } else {
            return false;
        }
    }
}
