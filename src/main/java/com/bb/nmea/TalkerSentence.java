/*
 * Copyright 2022 Scott Alan Stanley
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
 * The abstract parent class for all talker sentences. Provides the logic for obtaining 
 * type for the sentence as well as the talker ID.
 * 
 * @author Scott Stanley
 */
public abstract class TalkerSentence extends NMEASentence {

    public TalkerSentence(final String rawSentence) {
        super(rawSentence);
    }

    /**
     * Get the type code for this NMEA sentence.
     * 
     * @return The type code
     */
    @Override
    final protected String initSentenceId() {
        return getTag() != null ? TalkerSentence.getTypeFromTag(getTag()) : null;
    }
    
    /**
     * Get the talker ID of this NMEA sentence.
     * 
     * @return The talker ID
     */
    public String getTalkerId() {
        return getTag() != null ? getTag().substring(0, 2) : null;
    } 
    
    
    /**
     * Parse the NMEA sentence type from the provided tag value.
     * 
     * @param tag The NMEA tag value
     * @return The NMEA type field
     */
    static String getTypeFromTag(final String tag) {
        return tag.substring(2, 5);
    }
}
