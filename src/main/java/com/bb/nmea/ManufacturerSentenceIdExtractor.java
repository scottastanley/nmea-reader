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
package com.bb.nmea;

/**
 * An interface defining the method used to extract the manufacturer defined
 * sentence ID for a proprietary sentence. A class implementing this interface
 * must be defined for each manufacturer whose proprietary 
 * sentence is to be parsed. Every sentence defined for the manufacturer
 * must return the same class. This logic is used in the sentence factory
 * to determine the proper sentence class.
 * 
 * @author Scott Stanley
 */
public interface ManufacturerSentenceIdExtractor {
    
    /**
     * Get the manufacturer defined sentence ID for the provided 
     * raw sentence.
     * 
     * @param rawSentence The raw sentence
     * @return The manufacturer defined sentence ID
     */
    public String getManufacturerSentenceId(final String rawSentence);
}
