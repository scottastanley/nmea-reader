package com.bb.nmea;

/**
 * An enumeration of heading types used in the NMEA sentences.
 * 
 * @author Scott A Stanley
 */
public enum HeadingType {
    MAGNETIC("M"),
    TRUE("T");

    private String m_rawHeadingType;
    
    /**
     * Create a new instance of HeadingType
     * 
     * @param rawHeadingType The raw heading string for this heading type
     */
    HeadingType(final String rawHeadingType) {
        m_rawHeadingType = rawHeadingType;
    }
    
    /**
     * Get the correct HeadingType value based on the raw heading type string.
     * 
     * @param rawHeadingType The raw heading type, M or T.
     * @return The correct enumeration value
     */
    public static HeadingType getHeadingType(final String rawHeadingType) {
        if (rawHeadingType.equals(HeadingType.MAGNETIC.m_rawHeadingType)) {
            return HeadingType.MAGNETIC;
        } else if (rawHeadingType.equals(HeadingType.TRUE.m_rawHeadingType)) {
            return HeadingType.TRUE;
        } else {
            throw new RuntimeException("Unsupported rawHeadingType: " + rawHeadingType);
        }
    }
    
    /**
     * Get the name of this heading type.
     * 
     * @return the name
     */
    public String getName() {
        return m_rawHeadingType;
    }
}
