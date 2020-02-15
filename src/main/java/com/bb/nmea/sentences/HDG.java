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
import com.bb.nmea.sentences.common.Direction;

/**
 * HDG : Heading, Deviation and Variation
 * 
 * @author Scott Stanley
 */
public class HDG 
        extends NMEASentence {
    private final Float m_headingDegrees;
    private final Float m_deviationDegrees;
    private final Direction m_deviationDirection;
    private final Float m_variationDegrees;
    private final Direction m_variationDirection;

    public HDG(String rawSentence) {
        super(rawSentence);
        
        m_headingDegrees = this.getFieldAsFloat(1);
        m_deviationDegrees = this.getFieldAsFloat(2);
        m_deviationDirection = this.getFieldAsDirection(3);
        m_variationDegrees = this.getFieldAsFloat(4);
        m_variationDirection = this.getFieldAsDirection(5);
    }

    /**
     * @return the headingDegrees
     */
    public Float getHeadingDegrees() {
        return m_headingDegrees;
    }

    /**
     * @return the deviationDegrees
     */
    public Float getDeviationDegrees() {
        return m_deviationDegrees;
    }

    /**
     * @return the deviationDirection
     */
    public Direction getDeviationDirection() {
        return m_deviationDirection;
    }

    /**
     * @return the variationDegrees
     */
    public Float getVariationDegrees() {
        return m_variationDegrees;
    }

    /**
     * @return the variationDirection
     */
    public Direction getVariationDirection() {
        return m_variationDirection;
    }
}
