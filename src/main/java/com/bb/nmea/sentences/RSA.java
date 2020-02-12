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
import com.bb.nmea.sentences.common.Status;
/**
 * RSA: Rudder Sensor Angle
 * 
 * @author Scott Stanley
 */
public class RSA extends NMEASentence {
    private final Float m_starboardRudderAngle;
    private final Status m_starboardRudderStatus;
    private final Float m_portRudderAngle;
    private final Status m_portRudderStatus;

    public RSA(String rawSentence) {
        super(rawSentence);
        
        m_starboardRudderAngle = this.getFieldAsFloat(1);
        m_starboardRudderStatus = this.getFieldAsStatus(2);
        
        if (this.getNumFields() > 3) {
            m_portRudderAngle = this.getFieldAsFloat(3);
            m_portRudderStatus = this.getFieldAsStatus(4);
        } else {
            m_portRudderAngle = null;
            m_portRudderStatus = null;
        }
    }

    /**
     * Get the angle of the starboard rudder.  Negative values mean turning
     * to port.
     * 
     * @return the starboardRudderAngle
     */
    public Float getStarboardRudderAngle() {
        return m_starboardRudderAngle;
    }

    /**
     * @return the starboardRudderStatus
     */
    public Status getStarboardRudderStatus() {
        return m_starboardRudderStatus;
    }

    /**
     * Get the angle of the port rudder.  Negative values mean turning
     * to port.
     * 
     * @return the portRudderAngle
     */
    public Float getPortRudderAngle() {
        return m_portRudderAngle;
    }

    /**
     * @return the portRudderStatus
     */
    public Status getPortRudderStatus() {
        return m_portRudderStatus;
    }
    
    /**
     * Is this RSA sentence reporting dual rudder measurements?
     * 
     * @return True of dual rudder data was reported
     */
    public Boolean isDualRudder() {
        if (m_portRudderAngle == null) {
            return Boolean.FALSE;
        } else {
            return Boolean.TRUE;
        }
    }
    
    /**
     * Get the single rudder angle.  In reality, this is the same value as the
     * starboard rudder angle since for single rudder sentences it is defined as 
     * a starboard rudder.  Negative values mean turning
     * to port.
     * 
     * @return the rudder angle for a single rudder sentence
     */
    public Float getRudderAngle() {
        return m_starboardRudderAngle;
    }

    /**
     * Get the single rudder status.  In reality, this is the same value as the
     * starboard rudder status since for single rudder sentences it is defined as 
     * a starboard rudder.
     * 
     * @return the rudder status for a single rudder sentence
     */
    public Status getRudderStatus() {
        return m_starboardRudderStatus;
    }
}
