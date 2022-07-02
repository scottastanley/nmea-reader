package com.bb.nmea;

public class UnparsableSentence extends NMEASentence {

    /**
     * Create an instance of UnsupportedSentence.
     * 
     * @param rawSentence The raw sentence
     */
    public UnparsableSentence(String rawSentence) {
        super(rawSentence, Boolean.FALSE);
    }

    /**
     * Get the sentence ID for this NMEA sentence.
     * 
     * @return The sentence ID
     */
    @Override
    protected String initSentenceId() {
        return "UNPARSABLE TAG: " + super.getTag();
    }
}
