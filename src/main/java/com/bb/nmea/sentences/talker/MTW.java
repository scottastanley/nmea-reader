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
package com.bb.nmea.sentences.talker;

import com.bb.nmea.TalkerSentence;
import com.bb.nmea.sentences.common.TemperatureUnits;

/**
 * MTW : Mean Temperature of the Water
 * 
 * @author Scott Stanley
 */
public class MTW extends TalkerSentence {
    private Float m_meanWaterTemp;
    private TemperatureUnits m_units;

    /**
     * Construct an instance of the MTW sentence.
     * 
     * @param rawSentence
     */
    public MTW(String rawSentence) {
        super(rawSentence);
        
        m_meanWaterTemp = this.getFieldAsFloat(1);
        m_units = this.getFieldAsTemperatureUnits(2);
    }

    /**
     * @return the meanWaterTemp
     */
    public Float getMeanWaterTemp() {
        return m_meanWaterTemp;
    }

    /**
     * @return the units
     */
    public TemperatureUnits getUnits() {
        return m_units;
    }
}
