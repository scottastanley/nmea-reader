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
import com.bb.nmea.sentences.common.SpeedUnits;
import com.bb.nmea.sentences.common.Status;
import com.bb.nmea.sentences.common.WindReference;

/**
 * MWV: Wind Speed and Angle
 * 
 * @author Scott Stanley
 */
public class MWV extends TalkerSentence {
    private final Float m_windAngle;
    private final WindReference m_windReference;
    private final Float m_windSpeed;
    private final SpeedUnits m_speedUnits;
    private final Status m_status;
    
    public MWV(String rawSentence) {
        super(rawSentence);
        
        m_windAngle = this.getFieldAsFloat(1);
        m_windReference = this.getFieldAsWindReference(2);
        m_windSpeed = this.getFieldAsFloat(3);
        m_speedUnits = this.getFieldAsSpeedUnits(4);
        m_status = this.getFieldAsStatus(5);
    }

    /**
     * Get the wind angle in the range 0 <= windAngle < 360.0.  Whether the wind
     * angle and speed is relative or true is defined by the wind reference.
     * 
     * @return The angle of the wind
     */
    public Float getWindAngle() {
        return m_windAngle;
    }

    /**
     * The indicator of whether this instance of the sentence is relative to the 
     * boat or whether it is true.
     * 
     * @return The wind reference
     */
    public WindReference getWindReference() {
        return m_windReference;
    }

    /**
     * The wind speed. Whether the wind angle and speed is relative or 
     * true is defined by the wind reference.  The units of measurement 
     * for the wind speed is defined by the speed units field.
     * 
     * @return The wind speed
     */
    public Float getWindSpeed() {
        return m_windSpeed;
    }

    /**
     * The units of measurements for the wind speed.
     * 
     * @return The speed units
     */
    public SpeedUnits getSpeedUnits() {
        return m_speedUnits;
    }

    /**
     * The status for this measurement.
     * 
     * @return the status
     */
    public Status getStatus() {
        return m_status;
    }
}
