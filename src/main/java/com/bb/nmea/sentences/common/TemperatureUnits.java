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
 * An enumeration of temperature units.
 * 
 * @author Scott Stanley
 */
public enum TemperatureUnits {
    CELCIUS("C");
    
    private final String m_rawValue;
    
    /**
     * Construct a new TemperatureUnits.
     * 
     * @param rawValue The raw value for this instance
     */
    private TemperatureUnits(final String rawValue) {
        m_rawValue = rawValue;
    }
    
    /**
     * Get the enumeration value for the provided raw string.
     * 
     * @param rawValue The raw value
     * @return The enumeration
     */
    public static TemperatureUnits getTemperatureUnits(final String rawValue) {
        if (rawValue.equals(CELCIUS.m_rawValue)) {
            return TemperatureUnits.CELCIUS;
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
