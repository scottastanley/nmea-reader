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

/**
 * DPT : Depth of water
 * 
 * @author Scott Stanley
 */
public class DPT 
        extends TalkerSentence {
    private final Float m_depthBelowTransducer;
    private final Float m_transducerOffset;

    public DPT(String rawSentence) {
        super(rawSentence);
        
        m_depthBelowTransducer = this.getFieldAsFloat(1);
        m_transducerOffset = this.getFieldAsFloat(2);
    }

    /**
     * Get the depth of the water below the transducer in meters.
     * 
     * @return The depth of the water below the transducer in meters
     */
    public Float getDepthBelowTransducer() {
        return m_depthBelowTransducer;
    }

    /**
     * Get the transducer offset in meters.  A positive value means this is the offset 
     * between the waterline and the transducer while a negative value indicates the 
     * offset between the transducer and the keel.
     * 
     * @return The transducer offset in meters
     */
    public Float getTransducerOffset() {
        return m_transducerOffset;
    }
}
