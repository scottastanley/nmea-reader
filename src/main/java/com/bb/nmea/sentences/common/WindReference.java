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

public enum WindReference {
    RELATIVE("R"),
    TRUE("T");
    
    private final String m_rawValue;
    
    private WindReference(final String rawValue) {
        m_rawValue = rawValue;
    }
    
    /**
     * Get the enumeration value for the provided raw string.
     * 
     * @param rawValue The raw value
     * @return The enumeration
     */
    public static WindReference getWindReference(final String rawValue) {
        if (rawValue.equals(WindReference.RELATIVE.m_rawValue)) {
            return WindReference.RELATIVE;
        } else if (rawValue.equals(WindReference.TRUE.m_rawValue)) {
            return WindReference.TRUE;
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
