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

import com.bb.nmea.NMEASentence;
import com.bb.nmea.sentences.common.HeadingType;

/**
 * HDM: Magnetic Heading
 * 
 * @author Scott A Stanley
 */
public class HDM extends NMEASentence {
    private final Float m_headingDegrees;
    private final HeadingType m_headingType;

    public HDM(final String rawSentence) {
        super(rawSentence);
        
        m_headingDegrees = this.getFieldAsFloat(1);
        m_headingType = HeadingType.getHeadingType(this.getField(2));
    }

    /**
     * Get the heading in degrees.
     * 
     * @return The heading in degrees
     */
    public Float getHeadingDegrees() {
        return m_headingDegrees;
    }

    /**
     * Get the heading type, magnetic or true.
     * 
     * @return The heading type
     */
    public HeadingType getHeadingType() {
        return m_headingType;
    }
}
