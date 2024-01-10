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
 * The abstract parent class for all proprietary sentences. Provides the logic for obtaining 
 * type for the sentence.
 * 
 * @author Scott Stanley
 */
public abstract class ProprietarySentence extends NMEASentence {
    private String m_manufacturerId;

    /**
     * Create the proprietary sentence
     * 
     * @param rawSentence The raw sentence string
     */
    public ProprietarySentence(String rawSentence) {
        super(rawSentence);
    }

    /**
     * Get the sentence ID for this NMEA sentence.
     * 
     * @return The sentence ID
     */
    @Override
    final protected String initSentenceId() {
        
        // Extract the manufacturer ID
        String tag = getTag();
        m_manufacturerId = tag != null ? tag.substring(1,4) : null;

        // Get the manufacturer specific sentence ID
        String manufactSentenceId = getManufacturerSentenceId();
        manufactSentenceId = manufactSentenceId != null ? manufactSentenceId : "null";
        
        return m_manufacturerId + "-" + manufactSentenceId;
    }
    
    /**
     * Get the sentence ID defined by the manufacturer.
     * 
     * @return The sentence ID defined by the manufacturer
     */
    public abstract String getManufacturerSentenceId();
    
    /**
     * Get the Manufacturer ID for this proprietary sentence.
     * 
     * @return The Manufacturer ID
     */
    public String getManufacturerID() {
        return m_manufacturerId;
    }
}
