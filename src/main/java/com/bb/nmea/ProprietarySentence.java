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

    ProprietarySentence(String rawSentence) {
        super(rawSentence);
    }

    /**
     * Get the sentence ID for this NMEA sentence.
     * 
     * @return The sentence ID
     */
    @Override
    abstract protected String initSentenceId();
    
    /**
     * Utility method to get the base form of the sentence ID from the tag.  Basically,
     * this returns the tag minus the leading P indicating it is proprietary.
     * 
     * @return The portion of the tag excluding the leading P
     */
    protected String getSentenceIdFromTag() {
        String tag = getTag();
        return tag != null ? tag.substring(1) : null;
    }
}