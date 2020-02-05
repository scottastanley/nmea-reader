package com.bb.nmea.sentences;

import com.bb.nmea.NMEASentence;

/**
 * UnsupportedSentence is the NMEASentence type which is returned if no explicit
 * NMEASentence implementation can be found for the tag from the raw sentence
 * string.
 * 
 * @author Scott Stanley
 */
public class UnsupportedSentence 
        extends NMEASentence {

    /**
     * Create an instance of UnsupportedSentence.
     * 
     * @param rawSentence The raw sentence
     */
    public UnsupportedSentence(String rawSentence) {
        super(rawSentence);
    }
    
    /**
     * Get the field values from this sentence.  The indexes
     * of fields in the sentence begin as 0 with the tag field.
     * 
     * @param n The index of the field to obtain the value for
     * @return The value of the field as a string
     */
    public String getUnknownField(final int n) {
        return this.getField(n);
    }
}
