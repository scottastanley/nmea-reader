package com.bb.nmea.sentences;

import com.bb.nmea.NMEASentence;

public class UnparsableSentence extends NMEASentence {

    /**
     * Create an instance of UnsupportedSentence.
     * 
     * @param rawSentence The raw sentence
     */
    public UnparsableSentence(String rawSentence) {
        super(rawSentence);
    }

    @Override
    public String getTypeCode() {
        return "UNPARSABLE TAG: " + super.getTag();
    }
}
