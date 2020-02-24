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

public enum SpeedUnits {
    KNOTS("N"),
    KM_PER_HR("K");
    
    private final String m_rawValue;
    
    private SpeedUnits(final String rawValue) {
        m_rawValue = rawValue;
    }
    
    /**
     * Get the enumeration value for the provided raw string.
     * 
     * @param rawValue The raw value
     * @return The enumeration
     */
    public static SpeedUnits getSpeedUnits(final String rawValue) {
        if (rawValue.equals(SpeedUnits.KNOTS.m_rawValue)) {
            return SpeedUnits.KNOTS;
        } else if (rawValue.equals(SpeedUnits.KM_PER_HR.m_rawValue)) {
            return SpeedUnits.KM_PER_HR;
        } else {
            throw new RuntimeException("Unsupported raw value: " + rawValue);
        }
    }
    /**
     * @return the rawValue
     */
    public String getRawValue() {
        return m_rawValue;
    }
}
