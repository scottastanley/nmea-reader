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
package com.bb.nmea;

/**
 * An enumeration of heading types used in the NMEA sentences.
 * 
 * @author Scott A Stanley
 */
public enum HeadingType {
    MAGNETIC("M"),
    TRUE("T");

    private String m_rawHeadingType;
    
    /**
     * Create a new instance of HeadingType
     * 
     * @param rawHeadingType The raw heading string for this heading type
     */
    HeadingType(final String rawHeadingType) {
        m_rawHeadingType = rawHeadingType;
    }
    
    /**
     * Get the correct HeadingType value based on the raw heading type string.
     * 
     * @param rawHeadingType The raw heading type, M or T.
     * @return The correct enumeration value
     */
    public static HeadingType getHeadingType(final String rawHeadingType) {
        if (rawHeadingType.equals(HeadingType.MAGNETIC.m_rawHeadingType)) {
            return HeadingType.MAGNETIC;
        } else if (rawHeadingType.equals(HeadingType.TRUE.m_rawHeadingType)) {
            return HeadingType.TRUE;
        } else {
            throw new RuntimeException("Unsupported rawHeadingType: " + rawHeadingType);
        }
    }
    
    /**
     * Get the name of this heading type.
     * 
     * @return the name
     */
    public String getName() {
        return m_rawHeadingType;
    }
}
