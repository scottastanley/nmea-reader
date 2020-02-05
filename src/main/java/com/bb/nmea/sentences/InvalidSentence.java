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
        super(rawSentence);
        
        this.setInValid();
    }
}
