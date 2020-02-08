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

/**
 * A sub-class of NMEASentence reserved for use with NMEA sentences which 
 * do not pass basic validation.  For an NMEA sentence to be considered
 * valid, it must match the pattern defined in the NMEASentence class
 * and the checksum must be able to be verified.
 * 
 * @author Scott A Stanley
 */
public class InvalidSentence extends NMEASentence {

    /**
     * Construct a new InvalidSentence.
     * 
     * @param rawSentence The raw NMEA sentence
     */
    public InvalidSentence(String rawSentence) {
        super(rawSentence, Boolean.FALSE);
    }
}
