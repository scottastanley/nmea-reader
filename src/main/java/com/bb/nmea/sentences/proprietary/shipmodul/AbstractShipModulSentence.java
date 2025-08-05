/*
 * Copyright 2025 Scott Alan Stanley
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
package com.bb.nmea.sentences.proprietary.shipmodul;

import com.bb.nmea.ProprietarySentence;

/**
 * The abstract base class for all ShipModul proprietary sentences.
 * 
 * @author Scott Stanley
 */
public abstract class AbstractShipModulSentence extends ProprietarySentence {

	/**
	 * 
	 * @param rawSentence
	 */
	public AbstractShipModulSentence(String rawSentence) {
		super(rawSentence);
	}

    /**
     * Get the sentence ID defined by the manufacturer.
     * ShipModul concatenates their unique identifier
     * to the end of the NMEA tag, prior to the first comma.
     * 
     * ex:
     * $PSMDCN -> CN
     * $PSMDDR -> DR
     * 
     * @return The sentence ID defined by the manufacturer
     */
	@Override
	public String getManufacturerSentenceId() {
        int firstCommaIndx = getRawSentence().indexOf(",");
        

		return getRawSentence().substring(5, firstCommaIndx);
	}
}
