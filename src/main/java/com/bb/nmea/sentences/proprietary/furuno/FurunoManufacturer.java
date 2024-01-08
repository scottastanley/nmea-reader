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

import com.bb.nmea.ProprietarySentenceManufacturer;

/**
 * The manufactorer definition for Furuno.
 * 
 * @author Scott Stanley
 */
public class FurunoManufacturer implements ProprietarySentenceManufacturer {
    
    /** 
     * Construct an instance.
     */
    FurunoManufacturer() {
    }

    
    /**
     * Get the Furuno defined sentence ID for the provided 
     * raw sentence.
     * 
     * @param rawSentence The raw sentence
     * @return The manufacturer defined sentence ID
     */
    @Override
    public String getManufacturerSentenceId(String rawSentence) {
        int firstCommaIndx = rawSentence.indexOf(",");
        int secondCommaIndx = rawSentence.indexOf(",", firstCommaIndx+1);
        return rawSentence.substring(firstCommaIndx+1, secondCommaIndx);
    }

}
