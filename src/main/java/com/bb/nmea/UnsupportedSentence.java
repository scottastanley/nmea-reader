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
package com.bb.nmea;

/**
 * UnsupportedSentence is the NMEASentence type which is returned if no explicit
 * NMEASentence implementation can be found for the tag from the raw sentence
 * string.
 * 
 * @author Scott Stanley
 */
public class UnsupportedSentence 
        extends NMEASentence {

    /**
     * Create an instance of UnsupportedSentence.
     * 
     * @param rawSentence The raw sentence
     */
    public UnsupportedSentence(String rawSentence) {
        super(rawSentence);
    }
    
    /**
     * Get the field values from this sentence.  The indexes
     * of fields in the sentence begin as 0 with the tag field.
     * 
     * @param n The index of the field to obtain the value for
     * @return The value of the field as a string
     */
    public String getUnknownField(final int n) {
        return this.getField(n);
    }
    
    /**
     * Get the sentence ID for this NMEA sentence.
     * 
     * @return The sentence ID
     */
    @Override
    protected String initSentenceId() {
        return "UNSUPPORTED TAG: " + super.getTag();
    }
}
