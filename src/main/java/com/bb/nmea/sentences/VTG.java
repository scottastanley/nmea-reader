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
import com.bb.nmea.sentences.common.FAAModeIndicator;

/**
 * VTG: Track made good and ground speed.
 * 
 * @author Scott Stanley
 */
public class VTG 
        extends TalkerSentence {
    private final Float m_courseOverGroundDegrTrue;
    private final Float m_courseOverGroundDegrMagnetic;
    private final Float m_speedOverGroundKnots;
    private final Float m_speedOverGroundKmPerHr;
    private final FAAModeIndicator m_faaModeIndicator;

    /**
     * Construct a new instance from the raw sentence
     * 
     * @param rawSentence
     */
    public VTG(final String rawSentence) {
        super(rawSentence);
        
        // Primary format
        if (this.getNumFields() == 10) {
            m_courseOverGroundDegrTrue = this.getFieldAsFloat(1);
            m_courseOverGroundDegrMagnetic = this.getFieldAsFloat(3);
            m_speedOverGroundKnots = this.getFieldAsFloat(5);
            m_speedOverGroundKmPerHr = this.getFieldAsFloat(7);
            m_faaModeIndicator = this.getFieldAsFAAModeIndicator(9);
        }
        // Legacy sentences may have the following format;
        // VTG,trueCourseDegr,magCourseDegr,speedOverGroundKnots,speedOverGroundKmPerHr*hh
        else {
            m_courseOverGroundDegrTrue = this.getFieldAsFloat(1);
            m_courseOverGroundDegrMagnetic = this.getFieldAsFloat(2);
            m_speedOverGroundKnots = this.getFieldAsFloat(3);
            m_speedOverGroundKmPerHr = this.getFieldAsFloat(4);
            m_faaModeIndicator = null;
        }
    }

    /**
     * @return the courseOverGroundDegrTrue
     */
    public Float getCourseOverGroundDegrTrue() {
        return m_courseOverGroundDegrTrue;
    }

    /**
     * @return the courseOverGroundDegrMagnetic
     */
    public Float getCourseOverGroundDegrMagnetic() {
        return m_courseOverGroundDegrMagnetic;
    }

    /**
     * @return the speedOverGroundKnots
     */
    public Float getSpeedOverGroundKnots() {
        return m_speedOverGroundKnots;
    }

    /**
     * @return the speedOverGroundKmPerHr
     */
    public Float getSpeedOverGroundKmPerHr() {
        return m_speedOverGroundKmPerHr;
    }

    /**
     * @return the faaModeIndicator
     */
    public FAAModeIndicator getFaaModeIndicator() {
        return m_faaModeIndicator;
    }
}
