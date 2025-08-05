package com.bb.nmea.sentences.proprietary.shipmodul;

import com.bb.nmea.ProprietarySentenceManufacturer;

public class ShipModulManufacturer extends ProprietarySentenceManufacturer {

	public ShipModulManufacturer() {
		super("SMD");
	}

    /**
     * Get the ShipModul defined sentence ID for the provided 
     * raw sentence. ShipModul concatenates their unique identifier
     * to the end of the NMEA tag, prior to the first comma.
     * 
     * ex:
     * $PSMDCN -> CN
     * $PSMDDR -> DR
     * 
     * @param rawSentence The raw sentence
     * @return The manufacturer defined sentence ID
     */
	@Override
	public String getManufacturerSentenceId(final String rawSentence) {
        int firstCommaIndx = rawSentence.indexOf(",");
        

		return rawSentence.substring(5, firstCommaIndx);
	}
}
