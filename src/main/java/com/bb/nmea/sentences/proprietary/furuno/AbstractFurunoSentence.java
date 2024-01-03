/*
 * Copyright 2023 Scott Alan Stanley
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
package com.bb.nmea.sentences.proprietary.furuno;

import com.bb.nmea.ProprietarySentence;

/**
 * The abstract base class for all Furuno proprietary sentences.
 * 
 * @author Scott Stanley
 */
abstract public class AbstractFurunoSentence extends ProprietarySentence {
    AbstractFurunoSentence(final String rawSentence) {
        super(rawSentence);
    }
    
    /**
     * Get the sentence ID defined by the manufacturer.
     * For Furuno, they set the first field after the tag as a
     * sentence ID.
     * 
     * @return The sentence ID defined by the manufacturer
     */
    @Override    
    final public String getManufacturerSentenceId() {
        return this.getField(1);
    }
}
