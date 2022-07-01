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

/**
 * DBT : Depth Below Transducer
 * 
 * @author Scott Stanley
 */
public class DBT 
        extends TalkerSentence {
    private final Float m_waterDepthInFeet;
    private final Float m_waterDepthInMeters;
    private final Float m_waterDepthInFathoms;
    
    public DBT(String rawSentence) {
        super(rawSentence);
        
        m_waterDepthInFeet = this.getFieldAsFloat(1);
        m_waterDepthInMeters = this.getFieldAsFloat(3);
        m_waterDepthInFathoms = this.getFieldAsFloat(5);
    }

    /**
     * @return the waterDepthInFeet
     */
    public Float getWaterDepthInFeet() {
        return m_waterDepthInFeet;
    }

    /**
     * @return the waterDepthInMeters
     */
    public Float getWaterDepthInMeters() {
        return m_waterDepthInMeters;
    }

    /**
     * @return the waterDepthInFathoms
     */
    public Float getWaterDepthInFathoms() {
        return m_waterDepthInFathoms;
    }
}
