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
