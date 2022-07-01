package com.bb.nmea;

public abstract class TalkerSentence extends NMEASentence {

    public TalkerSentence(final String rawSentence) {
        super(rawSentence);
    }
    
    /**
     * Get the talker ID of this NMEA sentence.
     * 
     * @return The talker ID
     */
    public String getTalkerId() {
        return getTag() != null ? getTag().substring(0, 2) : null;
    }
    
    /**
     * Get the type code for this NMEA sentence.
     * 
     * @return The type code
     */
    public String getTypeCode() {
        return getTag() != null ? NMEASentence.getTypeFromTag(getTag()) : null;
    }
}
